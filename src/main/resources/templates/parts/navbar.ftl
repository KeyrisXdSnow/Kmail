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
            <li class="nav-item">
                <a class="nav-link" href="/test">Test</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/greeting">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/messagesFromUser">Inbox</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/messagesToUser">Send</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/mainForm">Notes</a>
            </li>
            <li class="nav-item">
                <button id="open-button" type="button" class="btn btn-link" style="color: #868383"> Settings</button>
                <@settings.settingsWindow activeEmailName name></@settings.settingsWindow>
            </li>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/users/">UserList</a>
                </li>
            </#if>
            <li class="nav-item">
                <div class="page-wrapper">
                    <a class="btn trigger" href="#container" role="button">Send notes</a>
                </div>
            </li>
        </ul>
    </div>
    </#if>
</nav>
