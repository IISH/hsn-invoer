(function ($, PDFJS) {
    'use strict';

    var hsnCanvas;

    var readImageOrPdf = function (file) {
        var reader = new FileReader();

        if (file.type.match('image.*')) {
            reader.onload = function (evt) {
                hsnCanvas.loadImage(evt.target.result);
            };

            reader.readAsDataURL(file);
            return true;
        }

        if (file.type.indexOf('pdf') >= 0) {
            reader.onload = function () {
                PDFJS.getDocument(reader.result).then(function (pdf) {
                    pdf.getPage(1).then(function (page) {
                        var viewport = page.getViewport(5.0);

                        var hiddenCanvas = document.createElement('canvas');
                        hiddenCanvas.width = viewport.width;
                        hiddenCanvas.height = viewport.height;
                        var context = hiddenCanvas.getContext('2d');

                        var task = page.render({canvasContext: context, viewport: viewport});
                        task.promise.then(function () {
                            var data = hiddenCanvas.toDataURL('image/jpeg');
                            hsnCanvas.loadImage(data);
                        });
                    });
                });
            };

            reader.readAsArrayBuffer(file);
            return true;
        }

        return false;
    };

    $(document).ready(function () {
        hsnCanvas = new HsnCanvas('cutter', true);

        $('#cutter').closest('.canvas-container')
            .on('dragover', function (e) {
                if (e.preventDefault) {
                    e.preventDefault();
                }

                e.originalEvent.dataTransfer.dropEffect = 'copy';
                return false;
            })
            .on('drop', function (e) {
                e.stopPropagation();
                e.preventDefault();

                var found = false;
                var files = e.originalEvent.dataTransfer.files;

                for (var i = 0, f; f = files[i]; i++) {
                    if (!found) {
                        found = readImageOrPdf(f);
                    }
                }

                return false;
            });

        $('form:first').submit(function () {
            hsnCanvas.createNewImage(function (dataUrl) {
                sessionStorage.setItem('hsnScan', dataUrl);
            });
        });
    });
})(jQuery, PDFJS);