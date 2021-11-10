/*const LOGIN_URL = "login.html";


var db_usuarios = {};

var usuarioCorrente = {};


// Dados de usuários para serem utilizados por n ter o banco de dados do cadastro
const DadosBase = {
    usuarios: [
        { login: "admin", senha: "123"},
        { login: "user", senha: "123"},
    ]
};

function LoginIni () {
    usuarioCorrenteJSON = sessionStorage.getItem('usuarioCorrente');
    if (usuarioCorrenteJSON) {
        usuarioCorrente = JSON.parse (usuarioCorrenteJSON);
    }
    var usuariosJSON = localStorage.getItem('db_usuarios');
    if (!usuariosJSON) {  
            alert('BEM VINDO');
        db_usuarios = DadosBase;
        localStorage.setItem('db_usuarios', JSON.stringify (DadosBase));
    }
    else  {         
        db_usuarios = JSON.parse(usuariosJSON);    
    }
};
// Verificar LOGIN
function LoginVal (login, senha) {
    for (var i = 0; i < db_usuarios.usuarios.length; i++) {
        var usuario = db_usuarios.usuarios[i];
        if (login == usuario.login && senha == usuario.senha) {
            window.location.href = "userPage.html"
            sessionStorage.setItem ('usuarioCorrente', JSON.stringify (usuarioCorrente));
            return true;
        }
    }
    return false;
}

function setUserPass () {}



// Inicializa as estruturas utilizadas pelo LoginApp
LoginIni ();
function RodaLogin (event) {                
    event.preventDefault ();

    // Obtem os dados de login e senha a partir do formulário de login
    var username = document.getElementById('bdemail').value;
    var password = document.getElementById('bdpassword').value;

    // Valida login e se estiver ok, redireciona para tela inicial da aplicação
    resultadoLogin = LoginVal (username, password);
    if (resultadoLogin) {
        window.location.href = "userPage.html" ;
    }
    else { // Se login falhou, avisa ao usuário
        alert ('Usuário ou senha invalidos/para entrar na pagina usuario:user senha:123');
    }
}
document.getElementById ('bttlogin').addEventListener ('click', RodaLogin);*/

const form = document.getElementById("formLogin");

function getUser() {
    console.log("chama");
    let email = document.getElementById("username").value;
    let password = document.getElementById("senha").value;
    let actionSrc = `/usuario/${email}`;
    console.log(email + " " + password);
    
    // let xhr = new XMLHttpRequest();

    // xhr.addEventListener( 'load', function ( event ) {
    //     verifyUser(event.target.responseText, password);
    // } );

    // // Define case error
    // xhr.addEventListener( ' error', function ( event ) {
    //     alert ( 'Oops! ' );
    // } );

    // xhr.open( "GET", actionSrc);

    // xhr.send();

    $.ajax({
        url: `http://0.0.0.0:4567/usuario/${email}`,
        type: "POST",
        data: JSON.stringify({ username: email, senha: password}),
            //contentType: "application/json; charset=utf-8"
            headers: {                "Content-Type": "application/json",

            }
        //dataType: "json"
    }).done(function (data) {
    	   //alert(data);
    	   console.log("teste");
    	   localStorage.setItem('email', email);
           window.location.replace("/userPage.html");
        }).fail(function(error) {
    alert( error );
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