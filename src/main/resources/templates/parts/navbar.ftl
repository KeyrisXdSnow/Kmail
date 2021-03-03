<#include "security.ftl">
<#import "../menu/settings.ftl" as settings>
<#import "../menu/sendMessage.ftl" as sendMessage>

<nav class="navbar navbar-expand-lg navbar-light bg-light">

    <nav class="navbar navbar-light bg-light">
        <a class="navbar-brand" href="#">
            <img src="/static/picture/logo.jpg" alt="KMail" style="width: 80%">
        </a>
    </nav>
    

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <#if name??>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
           <!--
            <li class="nav-item">
                <a class="nav-link" href="/test">Test</a>
            </li>
            -->
            <li class="nav-item">

                <a class="nav-link" href="/greeting"> <img src="/static/picture/home.png" style="z-index: 9000;"> Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/messagesFromUser"> <img src="/static/picture/inbox.png" style="z-index: 9000;"> Inbox</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/messagesToUser">  <img src="/static/picture/sended.png" style="z-index: 9000;"> Send</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/mainForm">
                    <img src="/static/picture/notes.png" style="z-index: 9000;"> Notes
                </a>
            </li>
            <li class="nav-item">
                <button id="open-button" type="button" class="btn btn-link" style="color: #868383;text-decoration: #e1eaed">
                    <img src="/static/picture/settngs.png" style="z-index: 9000;"> Settings
                </button>
                <@settings.settingsWindow activeEmailName name></@settings.settingsWindow>
            </li>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/users/">UserList</a>
                </li>
            </#if>
            <li class="nav-item">
                <div class="page-wrapper">
                    <a class="btn trigger" href="#container" role="button" style="color: #858282">
                        <img src="/static/picture/send.png" style="z-index: 9000;"> Send message
                    </a>
                </div>
            </li>
        </ul>
    </div>
    </#if>
</nav>
