(function ($, PDFJS) {
    'use strict';

    var hsnCanvas;
    var cutter;

    var readImageOrPdf = function (file) {
        if (file.type.match('image.*')) {
            $.imageBlobToDataUrl(file, function (dataUrl) {
                hsnCanvas.loadImage(dataUrl);
            });
            return true;
        }

        if (file.type.indexOf('pdf') >= 0) {
            var reader = new FileReader();
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
                            var data = hiddenCanvas.toDataURL('image/png');
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
        cutter = $('#cutter');

        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if ((this.readyState === 4) && (this.status === 200)) {
                readImageOrPdf(this.response);
            }
        };
        xhr.open('GET', cutter.data('image-url'));
        xhr.responseType = 'blob';
        xhr.send();

        $('form:first').submit(function () {
            hsnCanvas.createNewImage(function (dataUrl) {
                if (cutter.hasClass('sideA'))
                    sessionStorage.setItem('hsnScanA', dataUrl);
                else
                    sessionStorage.setItem('hsnScanB', dataUrl);
            });
        });
    });
})(jQuery, PDFJS);