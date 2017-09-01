(function ($, PDFJS, LZString) {
    'use strict';

    var hsnCanvas;
    var cutter;
    var submit = false;

    function readImageOrPdf(file) {
        if (file.type.match('image.*')) {
            $.imageBlobToDataUrl(file, function (dataUrl) {
                hsnCanvas.loadImage(dataUrl);
            });
            return true;
        }

        if (file.type.indexOf('pdf') >= 0) {
            var reader = new FileReader();
            reader.onload = function onPdfLoaded() {
                PDFJS.getDocument(reader.result).then(function (pdf) {
                    pdf.getPage(1).then(function (page) {
                        var viewport = page.getViewport(3);

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
    }

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

        $('form:first').submit(function onSubmit(e) {
            if (!submit && $(document.activeElement).hasClass('btn-next')) {
                e.preventDefault();

                hsnCanvas.createNewImage(function (dataUrl) {
                    try {
                        var compressedDataUrl = LZString.compress(dataUrl);
                        if (cutter.hasClass('sideA')) {
                            sessionStorage.removeItem('hsnScanB'); // Also remove the old scan B already, just in case
                            sessionStorage.setItem('hsnScanA', compressedDataUrl);
                        }
                        else
                            sessionStorage.setItem('hsnScanB', compressedDataUrl);

                        submit = true;
                        $('.btn-next').click();
                    }
                    catch (error) {
                        console.error(error);
                        alert('Het is helaas niet gelukt om de scan op te slaan. ' +
                            'Dit kan gebeuren als de scan te groot is. ' +
                            'Na het knippen is er waarschijnlijk geen probleem meer!');
                    }
                });

                sessionStorage.setItem('hsnScanSide', 'A');
                sessionStorage.removeItem('hsnScanPositionA');
                sessionStorage.removeItem('hsnScanPositionB');
            }
        });
    });
})(jQuery, PDFJS, LZString);