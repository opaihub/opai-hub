const $ = id => document.getElementById(id);
const users = JSON.parse(localStorage.getItem('brcode_users') || '{}');
function save(){localStorage.setItem('brcode_users', JSON.stringify(users));}
function enter(){ $('auth').classList.add('hidden'); $('home').classList.remove('hidden'); }
$('login').onclick=()=>{const u=$('user').value.trim(),p=$('pass').value;if(!u||!p)return $('status').textContent='Preencha usuário e senha.';if(users[u]!==p)return $('status').textContent='Usuário ou senha inválidos.';localStorage.setItem('brcode_session',u);enter()};
$('register').onclick=()=>{const u=$('user').value.trim(),p=$('pass').value;if(!u||!p)return $('status').textContent='Preencha usuário e senha.';if(users[u])return $('status').textContent='Usuário já cadastrado.';users[u]=p;save();localStorage.setItem('brcode_session',u);enter()};
$('logout').onclick=()=>{localStorage.removeItem('brcode_session');$('home').classList.add('hidden');$('auth').classList.remove('hidden');$('pass').value='';};
if(localStorage.getItem('brcode_session'))enter();
