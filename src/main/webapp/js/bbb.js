$(document).ready(function() {
    $(".foto-clicavel").click(function(e) {
        seleciona(e.target);
    });
    $("#votar").click(function(e) {
        votar();
    });
    $("#mostrarParedao").click(function(e) {
        mostrarParedao();
    });
    $(".foto-clicavel").mouseenter(function(e) {
        ressalta(e.target);
    }).mouseleave(function() {
        limpa(e.target);
    });
    $(".foto-clicavel").mouseenter(ressalta(elem)).mouseleave(limpa(elem));
});

function limpa(elem) {
    $(elem).removeClass("selecionada");
}

function ressalta(elem) {
    $(".foto-clicavel").removeClass("selecionada");
    $(elem).addClass("selecionada");
}

function mostrarParedao() {
    $("#bemVindo").addClass("hide");
    $("#bemVindo").removeClass("bbb-container");
    $("#paredao").removeClass("hide");
    $("#paredao").addClass("bbb-container");
}

function seleciona(elem) {
    ressalta(elem);
    $("#votar").removeClass("hide");
}

function mostrarResultado() {
    $("#resultado").removeClass("hide");
    $("#resultado").addClass(".bbb-container-center");
    $("#participantes").addClass("hide");
    $("#acoesVotacao").addClass("hide");
    $("#acoesVotacao").addClass("hide");

    $.get(window.location.pathname + 'votacao/situacao', function(data) {
        var situacao = $.parseJSON(data);
        var percentualEsquerda = situacao.votosEsquerda / (situacao.votosEsquerda + situacao.votosDireita) * 100;
        var percentualDireita = situacao.votosDireita / (situacao.votosEsquerda + situacao.votosDireita) * 100;
        $("#votosParticipanteEsquerda").text(Math.round(percentualEsquerda) + "%");
        $("#votosParticipanteDireita").text(Math.round(percentualDireita) + "%");
        end = new Date(new Date().getTime() + situacao.tempoRestante);
    });
}

function votar() {
    var part_selecionado = $(".foto.selecionada").attr("data");
    if (part_selecionado === "esquerda") {
        $("#votado").text("Participante 1");
    } else if (part_selecionado === "direita") {
        $("#votado").text("Participante 2");
    }
    $.post(window.location.pathname + 'votacao/votar', 'part_id=' + part_selecionado, function() {
        mostrarResultado();
    })
            .error(function() {
        console.log("N�o foi poss�vel contabilizar o voto.");
    });
}

//timer
var end = Date.now();

var _second = 1000;
var _minute = _second * 60;
var _hour = _minute * 60;
var _day = _hour * 24;
var timer;

function showRemaining()
{
    var now = new Date();
    var distance = end.getTime() - now.getTime();
    if (distance < 0) {
        // handle expiry here..
        clearInterval(timer); // stop the timer from continuing ..
        //alert('Expired'); // alert a message that the timer has expired..
    }
    var days = Math.floor(distance / _day);
    var hours = Math.floor((distance % _day) / _hour);
    var minutes = Math.floor((distance % _hour) / _minute);
    var seconds = Math.floor((distance % _minute) / _second);
    //var milliseconds = distance % _second;
    if (days < 0)
        days = 0;
    if (hours < 0)
        hours = 0;
    if (minutes < 0)
        minutes = 0;
    if (seconds < 0)
        seconds = 0;

    var countdownElement = document.getElementById('timer');
    countdownElement.innerHTML = 'Tempo Restante: ' + days + 'd ' +
            hours + 'h ' +
            minutes + 'm ' +
            seconds + 's ';
}

timer = setInterval(showRemaining, 500);