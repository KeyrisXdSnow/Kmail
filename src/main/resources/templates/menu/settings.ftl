<#include "../parts/login.ftl">

<#macro modalWidowDep>

    <link rel='stylesheet prefetch' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css'>
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/animate.css/3.2.0/animate.min.css">

    <link rel="stylesheet" href="/static/css/modalWindow/settings.css">
</#macro>

<#macro settingsWindow email username>

    <div class="modal-overlay closed" id="modal-overlay"></div>

    <div class="setting-modal closed" id="setting-modal">
            <div class="list-group" style="margin-top: 3%">
                <a href="#" class="list-group-item flex-column align-items-start" style="border-top: none">
                    <div>
                        <h5><p class="text-center"> ${username}</p></h5>
                        <h7><p class="text-center"> ${email} </p></h7>
                    </div>
                </a>
                <a href="#" class="list-group-item list-group-item-action flex-column align-items-start">
                    <form action="/newEmail" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}" />

                        <div class="text-center">
                            <button type="submit" class="btn btn-link" type="submit">
                                <p class="text-center"> + Add new email </p>
                            </button>
                        </div>

                    </form>
                </a>
                <a href="#" class="list-group-item flex-column align-items-start">
                    <div class="text-center">
                        <@logout></@logout>
                    </div>
                </a>
            </div>
        <footer class="page-footer font-small blue">
            <!-- Copyright -->
            <div class="footer-copyright text-center py-3 ">
                <p style="font-size: x-small"> © 2020 Copyright:
                    <a href="https://vk.com/just___kristina"> VK </a>
                </p>
            </div>
            <!-- Copyright -->
        </footer>
    </div>

    <script src="/static/js/modelWindow/settings.js"></script>

</#macro>

