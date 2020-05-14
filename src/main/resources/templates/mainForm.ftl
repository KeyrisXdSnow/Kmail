<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <div>
        <@l.logout></@l.logout>
    </div>
    <div>
        <form method="post" action="add">
            <input type="text" name="text" placeholder="Введите сообщение" />
            <input type="text" name="tag" placeholder="Тэг">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <button type="submit">Добавить</button>
        </form>
    </div>
    <div>Список сообщений</div>
    <form method="get" action="mainForm">
        <input type="text" name="filter" value="${filter    }">
        <button type="submit">Найти</button>
    </form>
    <#if messages??>
        <#list messages as message>
            <b>${message.id}</b>
            <span>${message.text}</span>
            <i>${message.tag}</i>
            <strong>${message.authorName}</strong>
        </#list>
        <#else> No messages
    </#if>
</@c.page>