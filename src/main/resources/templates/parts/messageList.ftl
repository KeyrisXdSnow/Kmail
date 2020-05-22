<#import "messageListItem.ftl" as mlist>

<#macro messageListForm messages>

    <div class="content" style="position:absolute; left: 1%; width: 98%">
        <div class="list-group">
            <#list messages as message>
                <@mlist.item message></@mlist.item>
            </#list>
    </div>
    <form method="post" action="/additionMessages">
        <div>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />

            <button class="btn btn-outline-info" type="submit">
                <img src="picture/button-down.png">
                what
            </button>
        </div>
    </form>
</#macro>