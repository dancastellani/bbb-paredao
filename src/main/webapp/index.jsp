<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>BBB - Paredao</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/bbb.css" rel="stylesheet">

        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/jquery-2.0.3.min.js"></script>
        <script type="text/javascript" src="js/bbb.js"></script>
    </head>
    <body>
        <!--<img src="../img/bbb13_logo.jpg" alt="Logo BBB"/>-->

        <div id="paredao" class="bbb-container"> 
            <div id="cabecalho" class="bbb-container-top">
                QUEM DEVE SER <strong>ELIMINADO</strong>?
            </div>
            <div id="participantes" class="bbb-container-center">
                <div class="row">
                    <div class="span4">
                        <div class="foto foto-clicavel img-rounded  foto-esquerda" data="esquerda"></div>
                        Para eliminar o <strong>Participante 1</strong> pelo telefone
                        disque <strong>0800-123-001</strong> ou mande um SMS para <strong>8001</strong>
                        <br/>
                    </div>
                    <div class="span4">
                        <div class="foto foto-clicavel img-rounded  foto-direita" data="direita"></div>
                        Para eliminar o <strong>Participante 2</strong> pelo telefone
                        disque <strong>0800-123-002</strong> ou mande um SMS para <strong>8002</strong>
                        <br/>
                    </div>
                </div>

            </div>
            <div id="resultado" class="hide">
                <div>
                    <strong>Parabéns!</strong> Seu voto para o <strong><span id="votado"></span></strong> foi realizado com sucesso.
                </div>
                <div class="row">
                    <div class="span4">
                        <div class="foto img-rounded  foto-esquerda" data="esquerda"></div>
                        Votos:<span id="votosParticipanteEsquerda" ></span>
                    </div>

                    <div class="span4">
                        <div class="foto img-rounded  foto-direita" data="direita"></div>
                        Votos:<span id="votosParticipanteDireita" ></span>
                    </div>
                </div>
                <div id="timer">
                </div>
            </div>

            <div id="acoesVotacao" class="row bbb-container-botton">
                <div class="span8">
                    <a class="btn btn-large btn-primary hide" id="votar">Envie seu voto agora</a>
                </div>
            </div>
        </div>



    </body>
</html>
