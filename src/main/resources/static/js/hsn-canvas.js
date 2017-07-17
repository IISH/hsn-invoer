var HsnCanvas = (function ($, fabric) {
    'use strict';

    var State = {
        SELECTION: 1,
        ACTION: 2,
        LINE: 3
    };

    return function HsnCanvas(canvasId, allowCutting) {
        var canvas = new fabric.Canvas(canvasId, {stateful: false, renderOnAddRemove: false});

        var loading = new fabric.Text('Loading...',
            {hasControls: false, selectable: false, fontStyle: 'italic', fontFamily: 'Delicious'});
        var image = null;
        var lines = [];

        var state = State.SELECTION;
        var ctrlSelected = false;
        var activeLine = null;
        var newImagePositionCallbacks = [];

        setUpCanvasInteraction();

        this.loadImage = function loadImage(dataUrl, position) {
            if (image !== null)
                image.remove();

            fabric.Image.fromURL(dataUrl, function loadImageFromURL(img) {
                image = img;
                image.set({originX: 'center', originY: 'center'});

                canvas.remove(loading);
                canvas.add(image);

                if ($.isPlainObject(position))
                    image.set(position).setCoords();
                else if (allowCutting)
                    image
                        .scaleToWidth(canvas.width)
                        .set({
                            left: -image.getPointByOrigin('left', 'top').x,
                            top: -image.getPointByOrigin('left', 'top').y
                        })
                        .setCoords();
                else
                    image
                        .scaleToHeight(canvas.height)
                        .set({
                            left: -image.getPointByOrigin('left', 'top').x,
                            top: -image.getPointByOrigin('left', 'top').y
                        })
                        .setCoords();

                canvas.renderAll();

                setUpNavigation();
                setUpImageInteraction();
            });
        };

        this.onNewImagePosition = function onNewImagePosition(callback) {
            newImagePositionCallbacks.push(callback);
        };

        this.createNewImage = function createNewImage(callback) {
            function determineCutLine(point) {
                var curX = point;
                var height = curX - prevX;
                if (copy) {
                    imgs.push({top: lastCopiedX, data: getImageData(prevX, height)});
                    lastCopiedX += height;
                    newHeight += height;
                }
                prevX = curX;
                copy = !copy;
            }

            var imgs = [], newHeight = 0;
            scaleAll(1 / image.scaleX);

            var copy = true, prevX = 0, lastCopiedX = 0;
            getCutPoints().forEach(determineCutLine);
            determineCutLine(image.getHeight());

            var hiddenCanvas = document.createElement('canvas');
            hiddenCanvas.width = image.width;
            hiddenCanvas.height = newHeight;
            var context = hiddenCanvas.getContext('2d');

            var loaded = 0;
            imgs.forEach(function cutForImage(img) {
                var image = new Image();
                image.onload = function () {
                    context.drawImage(image, 0, img.top);
                    loaded++;

                    if (loaded === imgs.length) {
                        if (supportsImage('image/webp'))
                            callback(hiddenCanvas.toDataURL('image/webp', 80));
                        else if (supportsImage('image/jpeg'))
                            callback(hiddenCanvas.toDataURL('image/jpeg', 80));
                        else
                            callback(hiddenCanvas.toDataURL('image/png'));
                    }
                };
                image.src = img.data;
            });
            image.remove();
            image = null;

            lines.forEach(function (line) { line.remove(); });
            lines = [];
        };

        function setUpNavigation() {
            var keyCodes = [35, 36, 37, 38, 39, 40];
            if (allowCutting !== false) {
                keyCodes.push(13);
                keyCodes.push(46);
            }

            $(document).keydown(function (e) {
                if (e.keyCode === 17) {
                    ctrlSelected = true;
                    if (state === State.SELECTION) toActionState();
                }

                if (ctrlSelected && (keyCodes.indexOf(e.keyCode) >= 0)) {
                    e.preventDefault();

                    if (state === State.LINE) {
                        moveLine(e.keyCode);
                    }
                    else {
                        moveOrScale(e.keyCode);
                        canvas.trigger('object:modified', {target: image});
                    }

                    image.setCoords();
                    lines.forEach(function (line) { line.setCoords(); });
                    canvas.renderAll();
                }
            });

            $(document).keyup(function (e) {
                if (e.keyCode === 17) {
                    ctrlSelected = false;
                    if (state === State.ACTION) toSelectionState();
                }
            });
        }

        function setUpCanvasInteraction() {
            canvas.setBackgroundColor({source: '/images/canvas-bg.png', repeat: 'repeat'}, function () {
                canvas.renderAll();
            });
            canvas.setWidth($('.canvas-container').parent().width());

            canvas.add(loading);
            loading.center();

            canvas.renderAll();
        }

        function setUpImageInteraction() {
            image
                .on('selected', function () {
                    if (state === State.SELECTION) {
                        selectAll();
                    }
                })
                .on('mouseup', function (o) {
                    if ((state === State.ACTION) && (allowCutting !== false)) {
                        createCutLine(canvas.getPointer(o.e));
                    }
                });

            canvas.on('object:modified', function () {
                newImagePositionCallbacks.forEach(function (callback) {
                    callback({
                        top: image.top,
                        left: image.left,
                        scaleX: image.scaleX,
                        scaleY: image.scaleY
                    });
                });
            });
        }

        function toSelectionState() {
            state = State.SELECTION;

            canvas.defaultCursor = 'pointer';

            image.selectable = true;
            image.dirty = true;

            if (activeLine) {
                activeLine.stroke = 'blue';
                activeLine.dirty = true;
                activeLine = null;
            }

            canvas.deactivateAll().renderAll();
        }

        function toActionState() {
            state = State.ACTION;

            if (allowCutting !== false) {
                canvas.defaultCursor = 'crosshair';

                image.selectable = false;
                image.dirty = true;
            }

            if (activeLine) {
                activeLine.stroke = 'blue';
                activeLine.dirty = true;
                activeLine = null;
            }

            canvas.deactivateAll().renderAll();
        }

        function toLineState(line) {
            state = State.LINE;

            canvas.defaultCursor = 'crosshair';

            image.selectable = false;
            image.dirty = true;

            activeLine = line;
            activeLine.stroke = 'red';
            activeLine.dirty = true;

            canvas.deactivateAll().renderAll();
        }

        function moveLine(keyCode) {
            switch (keyCode) {
                case 38: // Up
                    if (image.getBoundingRect().top < (activeLine.getBoundingRect().top - 1)) {
                        activeLine.top -= 1;
                    }
                    break;
                case 40: // Down
                    if ((image.getBoundingRect().top + image.getBoundingRect().height) >
                        (activeLine.getBoundingRect().top + activeLine.getBoundingRect().height + 1)) {
                        activeLine.top += 1;
                    }
                    break;
                case 46: // Delete
                    lines.splice(lines.indexOf(activeLine), 1);
                    activeLine.remove();
                    toActionState();
                    break;
                case 13: // Enter
                    toActionState();
                    break;
            }
        }

        function moveOrScale(keyCode) {
            switch (keyCode) {
                case 37: // Left
                    image.left -= 5;
                    lines.forEach(function (line) {
                        line.left -= 5;
                    });
                    break;
                case 38: // Up
                    image.top -= 5;
                    lines.forEach(function (line) {
                        line.top -= 5;
                    });
                    break;
                case 39: // Right
                    image.left += 5;
                    lines.forEach(function (line) {
                        line.left += 5;
                    });
                    break;
                case 40: // Down
                    image.top += 5;
                    lines.forEach(function (line) {
                        line.top += 5;
                    });
                    break;
                case 35: // End
                    if (image.scaleX > 0) {
                        scaleAll(1 / 1.2);
                    }
                    break;
                case 36: // Home
                    scaleAll(1.2);
                    break;
            }
        }

        function selectAll() {
            if (allowCutting === true) {
                canvas.deactivateAll();

                var objs = canvas.getObjects().map(function (obj) { return obj.set('active', true); });
                var group = new fabric.Group(objs, {
                    originX: 'center',
                    originY: 'center',
                    lockUniScaling: true,
                    hasRotatingPoint: false
                });
                group.setCoords();

                canvas._currentTransform.target = group;
                canvas.setActiveGroup(group).renderAll();
            }
        }

        function createCutLine(pointer) {
            var closestLine = null;
            lines.forEach(function (line) {
                if (Math.abs(line.top - pointer.y) <= 10) {
                    closestLine = line;
                }
            });

            if (closestLine !== null) {
                toLineState(closestLine);
            }
            else if (lines.length < 3) {
                var bound = image.getBoundingRect();
                var curLine = new fabric.Line([bound.left, pointer.y, bound.width + bound.left, pointer.y], {
                    strokeWidth: 1,
                    stroke: 'blue',
                    originX: 'center',
                    originY: 'center',
                    selectable: false
                });
                lines.push(curLine);
                canvas.add(curLine).renderAll();
            }
        }

        function getCutPoints() {
            return lines
                .map(function (line) { return Math.round(line.getBoundingRect().top - image.getBoundingRect().top); })
                .sort(function (a, b) { return a - b; });
        }

        function getImageData(top, height) {
            return image.toDataURL({
                top: top,
                height: height
            });
        }

        function scaleAll(scaleFactor) {
            image.set({
                top: image.top * scaleFactor,
                left: image.left * scaleFactor,
                scaleX: image.scaleX * scaleFactor,
                scaleY: image.scaleY * scaleFactor
            });
            scaleLines(scaleFactor);
        }

        function scaleLines(scaleFactor) {
            var bound = image.setCoords().getBoundingRect();
            lines.forEach(function (line) {
                line.scaleX = 1;
                line.scaleY = 1;
                line.top = line.top * scaleFactor;
                line.left = line.left * scaleFactor;
                line.width = bound.width;
                line.setCoords();
            });
        }

        function supportsImage(mime) {
            var canvas = document.createElement('canvas');
            canvas.width = canvas.height = 1;
            var uri = canvas.toDataURL(mime);
            return (uri.match(mime) !== null);
        }
    };
})(jQuery, fabric);