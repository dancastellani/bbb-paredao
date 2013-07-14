function seleciona(elem) {
    $(".foto").removeClass("selecionada");
    $(elem).addClass("selecionada");
    $("#votar").removeClass("hide");
}

function mostrarResultado(){
    $("#paredao").addClass("hide");
    $("#resultado").removeClass("hide");
}

function votar(){
    var part_selecionado = $(".foto.selecionada").attr("data");
    $("#votado").text(part_selecionado);
    $.post('/votar', 'part_id='+part_selecionado, function () {
    	mostrarResultado();
    })
    .error(function () { console.log("Não foi possível contabilizar o voto."); });
    mostrarResultado();
}

$(document).ready(function() {
    $(".foto").click(function(e) {
        seleciona(e.target);
    });
    $("#votar").click(function(e) {
        votar();
    });
});