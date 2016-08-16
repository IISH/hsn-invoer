var HsnCanvas = (function ($, fabric) {
    'use strict';

    return function (canvasId, allowCutting) {
        var canvas = new fabric.Canvas(canvasId);

        var image = null;
        var lines = [];

        var curLine = null;
        var activeLine = null;
        var noKeyup = false;

        setUpCanvasInteraction();

        this.loadImage = function (dataUrl) {
            var img = document.createElement('img');
            img.onload = function () {
                image = new fabric.Image(img).set({originX: 'center', originY: 'center'});
                //image.resizeFilters.push(new fabric.Image.filters.Resize({resizeType: 'lanczos', lanczosLobes: 2}));
                image.selectable = false;

                canvas.add(image);
                image.scaleToWidth(canvas.width).center().setCoords();
                canvas.renderAll();

                setUpNavigation();
            };
            img.src = dataUrl;
        };

        this.createNewImage = function (callback) {
            var imgs = [];
            var newHeight = 0;

            scaleAll(1 / image.scaleX); // image.filter[0].scaleX

            var copy = true;
            var prevX = 0;
            var lastCopiedX = 0;
            getCutPoints().forEach(function (point) {
                var curX = point;
                var height = curX - prevX;
                if (copy) {
                    imgs.push({top: lastCopiedX, data: getImageData(prevX, height)});
                    lastCopiedX += height;
                    newHeight += height;
                }
                prevX = curX;
                copy = !copy;
            });

            var height = (image.getHeight() - prevX);
            if (copy) {
                imgs.push({top: lastCopiedX, data: getImageData(prevX, height)});
                newHeight += height;
            }

            var hiddenCanvas = document.createElement('canvas');
            hiddenCanvas.width = image.width;
            hiddenCanvas.height = newHeight;
            var context = hiddenCanvas.getContext('2d');

            var loaded = 0;
            imgs.forEach(function (img) {
                var image = new Image();
                image.onload = function () {
                    context.drawImage(image, 0, img.top);
                    loaded++;

                    if (loaded === imgs.length) {
                        callback(hiddenCanvas.toDataURL('image/jpeg'));
                    }
                };
                image.src = img.data;
            });
            image.remove();
            image = null;

            lines.forEach(function (line) {
                line.remove();
            });
            lines = [];
        };

        function setUpNavigation() {
            var keyCodes = [37, 38, 39, 40, 187, 189];
            if (allowCutting !== false) {
                keyCodes.push(13);
                keyCodes.push(46);
            }

            $(document).keydown(function (e) {
                if ((curLine === null) && e.ctrlKey && (keyCodes.indexOf(e.keyCode) >= 0)) {
                    e.preventDefault();

                    if (activeLine !== null) {
                        switch (e.keyCode) {
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
                                activeLine = null;
                                break;
                            case 13: // Enter
                                activeLine.stroke = 'blue';
                                activeLine = null;
                                noKeyup = true;
                                break;
                        }
                    }
                    else {
                        switch (e.keyCode) {
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
                            case 189: // Minus
                                if (image.scaleX > 0) {
                                    scaleAll(1 / 1.2);
                                }
                                break;
                            case 187: // Plus
                                scaleAll(1.2);
                                break;
                        }
                    }

                    image.setCoords();
                    lines.forEach(function (line) {
                        line.setCoords();
                    });
                    canvas.renderAll();
                }
            });
        }

        function setUpCanvasInteraction() {
            canvas.setBackgroundColor({source: '/images/canvas-bg.png', repeat: 'repeat'}, function () {
                canvas.renderAll();
            });

            canvas.setWidth($('.canvas-container').parent().width());
            canvas.renderAll();

            canvas.on('mouse:over', function (e) {
                e.target.hoverCursor = 'crosshair';
            });

            if (allowCutting !== false) {
                canvas.on('mouse:up', function (o) {
                    if ((image !== null) && (activeLine === null)) {
                        var pointer = canvas.getPointer(o.e);

                        var closestLine = null;
                        lines.forEach(function (line) {
                            if (Math.abs(line.top - pointer.y) <= 10) {
                                closestLine = line;
                            }
                        });

                        if (closestLine !== null) {
                            closestLine.stroke = 'red';
                            activeLine = closestLine;
                        }
                        else if (lines.length < 3) {
                            var bound = image.getBoundingRect();
                            if ((pointer.x >= bound.left) && (pointer.x <= (bound.width + bound.left)) &&
                                (pointer.y >= bound.top) && (pointer.y <= (bound.height + bound.top))) {

                                curLine = new fabric.Line([bound.left, pointer.y, bound.width + bound.left, pointer.y], {
                                    strokeWidth: 1,
                                    stroke: 'blue',
                                    originX: 'center',
                                    originY: 'center'
                                });
                                curLine.selectable = false;
                                canvas.add(curLine);
                                canvas.renderAll();

                                lines.push(curLine);
                                curLine = null;
                            }
                        }
                    }
                });
            }
        }

        function getCutPoints() {
            var cutPoints = [];

            lines.forEach(function (line) {
                cutPoints.push(line.getBoundingRect().top - image.getBoundingRect().top);
            });

            cutPoints.sort(function (a, b) {
                return a - b;
            });

            return cutPoints;
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

            var bound = image.setCoords().getBoundingRect();
            lines.forEach(function (line) {
                line.top = line.top * scaleFactor;
                line.left = line.left * scaleFactor;
                line.width = bound.width;
                line.setCoords();
            });
        }
    };
})(jQuery, fabric);