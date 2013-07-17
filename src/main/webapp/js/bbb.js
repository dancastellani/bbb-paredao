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
    $("#paredao").css("height", "500");


    $.get(window.location.pathname + 'votacao/situacao', function(data) {
        var situacao = $.parseJSON(data);
        percentualDeVotosDaEsquerda = situacao.votosEsquerda / (situacao.votosEsquerda + situacao.votosDireita) * 100;
        var percentualDeVotosDaDireita = situacao.votosDireita / (situacao.votosEsquerda + situacao.votosDireita) * 100;
        $("#votosParticipanteEsquerda").text(Math.round(percentualDeVotosDaEsquerda) + "%");
        $("#votosParticipanteDireita").text(Math.round(percentualDeVotosDaDireita) + "%");
        end = new Date(new Date().getTime() + situacao.tempoRestante);

        drawGraph(percentualDeVotosDaEsquerda);
    });

    if (timerHandle)
        clearInterval(timerHandle);
    timeLeftMillis = data.timeLeftMillis;

    timerHandle = setInterval(refreshStandings(), 500);
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
var percentualDeVotosDaEsquerda;

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

    $("#tempoRestante").text(days + ':' + hours + ':' + minutes + ':' + seconds);
}

timer = setInterval(showRemaining, 1000);


// --- grafico de votos
function drawGraph(v) {
    var width = 260;
    var height = 174;
    var lineWidth = 40;

    var startAngle = 1 * Math.PI;
    var endAngle = 2 * Math.PI;
    var w2 = width / 2;
    var xy = w2;
    var radius = xy - lineWidth / 2;

    var a = v * Math.PI / 100; // Angle representing the value
    var eat = startAngle + a; // End angle

    var g = document.getElementById('graph').getContext('2d');

    g.lineWidth = lineWidth;
    g.shadowBlur = 2;
    g.shadowColor = "#fff";
    g.translate(2, 2); // leave some pixels of space

    // white 'background' fill and a little out to give a countour
    g.beginPath();
    g.fillStyle = "#fff";
    g.arc(xy, xy, (2 + radius + lineWidth / 2), 0, 2 * Math.PI, true);
    g.fill();

    // left end
    g.strokeStyle = "#ccc"; // bgColor;
    g.beginPath();
    g.arc(xy, xy, radius, .5 * Math.PI, eat, true);
    g.stroke();

    // right end
    g.strokeStyle = "#ff9a00"; // fgColor;
    g.beginPath();
    g.arc(xy, xy, radius, .5 * Math.PI, eat, false);
    g.stroke();

    // value labels
    var rr = radius - lineWidth / 2;
    var tx = w2 + (Math.cos(eat) * rr);
    var ty = radius + (Math.sin(eat) * rr);
    var ta = (2 * Math.PI + ((eat) - 1.5 * Math.PI));
    g.shadowBlur = 0;
    g.font = "bold 16px Arial";
    g.fillStyle = "#fff";
    g.translate(xy, xy)
    g.rotate(ta - .2);
    var tw = g.measureText(v + "%").width / 2;
    g.fillText(v.toFixed(0) + "%", 60 - tw, 4 - radius);
    g.rotate(.4);
    tw = g.measureText((100 - v) + "%").width / 2;
    g.fillText((100 - v).toFixed(0) + "%", 70 - tw, 8 - radius);
    g.rotate(-(ta + .2));
    g.translate(-xy, -xy)

    // static labels
//    g.fillStyle = "#999";
//    g.font = "11px Arial";
//    g.fillText("FALTAM", 110, 95);
//    g.fillText("PARA ENCERRAR A VOTAÇÃO", 52, 142);

    g.translate(-2, -2); // reset to 0,0
}
;

function updateCountdown() {
    var hours = Math.floor(timeLeftMillis / (1000 * 60 * 60));
    var mins = Math.floor(timeLeftMillis / (1000 * 60));
    var secs = Math.floor(timeLeftMillis / 1000);
    var hh = hours;
    var mm = mins - hours * 60;
    var ss = secs - mins * 60;
    var formatted = ('0' + hh).slice(-2) + ':'
            + ('0' + mm).slice(-2) + ':'
            + ('0' + ss).slice(-2);
    renderCountdown(formatted);
    timeLeftMillis = timeLeftMillis - 1000;
    if (timeLeftMillis < 0) {
        timeLeftMillis = 0;
    }
}
var timeLeftMillis = 0;
var timerHandle;

function renderCountdown(value) {
    var g = $('graph').getContext('2d');

    g.shadowBlur = 0;
    g.font = "28px Arial";
    g.fillStyle = "#fff";
    g.fillRect(77, 100, 110, 25);
    g.fillStyle = "#ff9a00";
    g.fillText(value, 77, 124);
}

function refreshStandings() {
    drawGraph(percentualDeVotosDaEsquerda);
    updateCountdown();
}