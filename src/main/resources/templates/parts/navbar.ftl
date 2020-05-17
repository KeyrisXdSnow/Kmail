<#include "security.ftl">
<#include "login.ftl">
<#include "../menu/index.ftl">

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <p class="navbar-brand" href="#">KMail</p>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>


    <#if name??>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/greeting">Home</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/mainForm">Inbox</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/mainForm">Send</a>
            </li>
            <#if isAdmin>
                <li class="nav-item active">
                    <a class="nav-link" href="/users/">UserList</a>
                </li>
            </#if>
            <li class="nav-item active">
                <div class="page-wrapper">
                    <a class="btn trigger" href="#" role="button">Send notes</a>
                </div>
                <@menu></@menu>
            </li>

        </ul>
        <div class="navbar-text mr-3">${name}</div>
        <@logout> </@logout>
    </div>
    </#if>
</nav>