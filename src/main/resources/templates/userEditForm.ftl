<#import "parts/common.ftl" as c>

<@c.page>
    User editor
    <form method="post" action="/users/">
        <input type="text" name="userId" value="${user.id}">
        <input type="text" name="username" value="${user.username}">
        <#list roles as role>
            <div>
                <label><input type="checkbox" name="${role}" checked="${user.roles?seq_contains(role)?string("cheked","")}"> ${role} </label>
            </div>
        </#list>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit" > Save </button>
    </form>
</@c.page>