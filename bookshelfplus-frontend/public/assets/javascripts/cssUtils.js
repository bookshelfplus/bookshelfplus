function copyToClipboard(content) {
    var aux = document.createElement('input');
    aux.setAttribute('value', content);
    aux.style.display = "none";
    document.body.appendChild(aux);
    aux.select();
    document.execCommand('copy');
    document.body.removeChild(aux);
}
function showTip(e, text) {
    var $i = $("<span>").text(text);
    $("body").append($i);
    var x = e.pageX - $i.outerWidth() / 2,
        y = e.pageY - $i.outerHeight() - 1;
    $i.css({
        "position": "absolute",
        "z-index": "10000",
        "top": y,
        "left": x,
        "color": "red",
        "font-size": "14px",
        "font-weight": "bold",
    });
    $i.animate({
        "top": y - 60,
        "left": x,
        "opacity": "0"
    }, 600, function() {
        $i.remove();
    });
    e.stopPropagation();
}