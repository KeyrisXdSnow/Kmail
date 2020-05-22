<#macro item message>
    <form action="/trashMessage" method="post">

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="hidden" name="mesId" value="${message.getMesId()}"/>
        <a href="${url}/${message.getMesId()}" class="list-group-item list-group-item-action flex-column align-items-start">
            <div class="d-flex w-100 justify-content-between">
                <h5 class="mb-1">${message.getSubject()}</h5>
                <small class="text-muted"> ${message.getDate()} </small>
            </div>
            <p class="mb-1"> ${message.getSnippet()}</p>
            <small class="text-muted">
                <button class="btn btn-link">
                    <img src="/static/picture/trash.png " alt="To trash" style="width: 80%">
                </button>
            </small>
        </a>
    </form>
</#macro>