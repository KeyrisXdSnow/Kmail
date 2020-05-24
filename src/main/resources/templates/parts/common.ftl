<#include "../menu/sendMessage.ftl">
<#import "../menu/settings.ftl" as settings>

<#import "../menu/sendMessage.ftl" as sendMessage>

<#macro page>
    <!DOCTYPE html>
    <html>

    <head lANG="en">
        <meta charset="UTF-8">

        <title>KMail</title>

        <link rel="stylesheet" href="/static/css/style.css">
        <link rel="stylesheet" href="/static/css/nav.css">

        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
              integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
              crossorigin="anonymous">

        <script src='https://code.jquery.com/jquery-2.2.4.min.js'></script>

        <!-- Sweet alert-->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
        <script src="https://cdn.jsdelivr.net/npm/promise-polyfill"></script>
        <script src="/static/sweetAlert/dist/sweetalert2.all.min.js"></script>
        <script src="/static/sweetAlert/dist/sweetalert2.min.js"></script>
        <link rel="stylesheet" href="/static/sweetAlert/dist/sweetalert2.min.css">

        <@settings.modalWidowDep></@settings.modalWidowDep>
        <@sendMessage.menuDep></@sendMessage.menuDep>
    </head>
    <body>

    <#if mesSend??>
        <script src="js/alerts/messageSend.js"></script>
    </#if>
    <#if mesNotSend??>
        <script src="js/alerts/messageNotSend.js"></script>
    </#if>
    <#if MesDelete??>
        <script src="js/alerts/messageDelete.js"></script>
    </#if>
    <#if mesNotDelete??>
        <script src="js/alerts/messageNotDelete.js"></script>
    </#if>


    <div class="allClassesContainer" style="height: 100vh;">
        <#include "navbar.ftl ">
        <div class="container mt-5 justify-content-center">
            <#nested>
        </div>
        <@sendMessage.menu></@sendMessage.menu>
    </div>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"
            integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
            crossorigin="anonymous"></script>

    <script>
    </script>
    </body>
    </html>



</#macro>