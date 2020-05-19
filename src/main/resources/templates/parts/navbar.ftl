<#include "security.ftl">
<#include "login.ftl">
<#include "../menu/index.ftl">
<#include "../menu/settings.ftl">


<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <img src="/static/picture/logo.jpg" alt="KMail" style="width: 7%">

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
                    <a class="nav-link" href="/sendMessage">Send</a>
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

                <div class="form-group">
                        <button id="open-button" type="button" class="btn btn-outline-light">
                            <img src="/static/picture/settings.png" width="10%">
                        </button>
                </div>

            </ul>
            <div class="navbar-text mr-3"> <@settingsWindow></@settingsWindow></div>


        </div>
    </#if>
</nav>