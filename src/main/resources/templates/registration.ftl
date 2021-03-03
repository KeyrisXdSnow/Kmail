<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <div class="mb-1 text-center"> Add new user </div>
    <#if userExist??>
        <script>
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: 'User with such username exist',
                showConfirmButton: false,
                timer: 2100
            })
        </script>
        <#else> <script>
            Swal.fire(
                'You have successfully logged in to KMail. An authorization code was sent to the mail you specified!',
                'You clicked the button!',
                'success'
            )
        </script>
    </#if>
    <@l.login "/registration" true />
</@c.page>