const form = document.getElementById("formLogin");

function getUser() {
    console.log("chama");
    let email = document.getElementById("username").value;
    let password = document.getElementById("senha").value;
    let actionSrc = `/usuario/${email}`;
    console.log(email + " " + password);

    $.ajax({
        url: `http://0.0.0.0:4567/usuario/${email}`,
        type: "POST",
        data: JSON.stringify({ username: email, senha: password }),
            headers: {                
                "Content-Type": "application/json",
            }
        //dataType: "json"
    }).done(function (data) {
    	   //alert(data);
    	   console.log("teste");
    	   localStorage.setItem('email', email);
           window.location.replace("/userPage.html");
        }).fail(function(error) {
    alert( "Senha inválida" );
  })
}

function verifyUser( user, password) {
    let objUser = JSON.parse(user);
    // Verificação de senha
    if(objUser.senha == password) {
        window.location.replace("/userPage.html");
        localStorage.setItem('email', JSON.stringify(objUser.email));
    } else {
        alert("Senha incorreta!");
        window.location.replace("html/login_cadastro/login.html");
    }
}

window.addEventListener("load", function () { 
    form.addEventListener( "submit", function ( event ) {
        event.preventDefault();
        getUser();
    });
});
