package com.brcode.app;

import android.app.*;
import android.os.*;
import android.content.*;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.InputType;
import android.view.*;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity {
  LinearLayout root, body;
  SharedPreferences prefs;
  final int GREEN=Color.rgb(0,255,110), DARK=Color.rgb(4,7,6), PANEL=Color.rgb(10,18,15), BORDER=Color.rgb(20,65,45), WHITE=Color.WHITE, MUTED=Color.rgb(150,165,158);

  int dp(float v){return (int)(v*getResources().getDisplayMetrics().density+0.5f);}
  TextView tv(String text,float size,int color){TextView t=new TextView(this);t.setText(text);t.setTextSize(size);t.setTextColor(color);t.setGravity(Gravity.CENTER_VERTICAL);return t;}
  GradientDrawable bg(int color,int stroke,int radius){GradientDrawable g=new GradientDrawable();g.setColor(color);g.setCornerRadius(dp(radius));if(stroke!=0)g.setStroke(dp(1),stroke);return g;}
  Button button(String text){Button b=new Button(this);b.setText(text);b.setTextColor(WHITE);b.setTextSize(15);b.setAllCaps(false);b.setGravity(Gravity.CENTER_VERTICAL);b.setPadding(dp(14),0,dp(14),0);b.setBackground(bg(PANEL,BORDER,14));return b;}
  LinearLayout.LayoutParams lp(int w,int h){return new LinearLayout.LayoutParams(w,h);}
  LinearLayout.LayoutParams margin(int w,int h,int top,int bottom){LinearLayout.LayoutParams p=lp(w,h);p.setMargins(0,dp(top),0,dp(bottom));return p;}

  @Override public void onCreate(Bundle state){super.onCreate(state);getWindow().setStatusBarColor(Color.BLACK);getWindow().setNavigationBarColor(Color.BLACK);prefs=getSharedPreferences("brcode",0);if(!prefs.contains("u")){prefs.edit().putString("u","Caua_").putString("p","123456").apply();}showHome(prefs.getString("u","Caua_"));}

  void base(){root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setBackgroundColor(DARK);root.setPadding(dp(16),dp(10),dp(16),dp(10));setContentView(root);}

  void showAuth(){
    base(); root.setGravity(Gravity.CENTER);
    TextView logo=tv("⚡ BRcode",34,GREEN);logo.setGravity(Gravity.CENTER);root.addView(logo,lp(-1,dp(55)));
    TextView sub=tv("Conecte. Converse. Crie.",16,MUTED);sub.setGravity(Gravity.CENTER);root.addView(sub,lp(-1,dp(40)));
    LinearLayout card=new LinearLayout(this);card.setOrientation(LinearLayout.VERTICAL);card.setPadding(dp(18),dp(18),dp(18),dp(18));card.setBackground(bg(PANEL,BORDER,18));root.addView(card,margin(-1,-2,16,0));
    EditText user=new EditText(this);user.setHint("Usuário");user.setText("Caua_");user.setSingleLine(true);user.setTextColor(WHITE);user.setHintTextColor(MUTED);user.setBackground(bg(Color.rgb(7,12,10),BORDER,12));user.setPadding(dp(12),0,dp(12),0);card.addView(user,lp(-1,dp(52)));
    EditText pass=new EditText(this);pass.setHint("Senha");pass.setText("123456");pass.setSingleLine(true);pass.setTextColor(WHITE);pass.setHintTextColor(MUTED);pass.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);pass.setBackground(bg(Color.rgb(7,12,10),BORDER,12));pass.setPadding(dp(12),0,dp(12),0);card.addView(pass,margin(-1,dp(52),10,0));
    Button login=button("Entrar");login.setBackground(bg(GREEN,0,12));login.setTextColor(Color.BLACK);login.setOnClickListener(v->{String u=user.getText().toString().trim(),p=pass.getText().toString();if(u.equals("Caua_")&&p.equals("123456")){showHome(u);return;}if(u.equals(prefs.getString("u",""))&&p.equals(prefs.getString("p","")))showHome(u);else toast("Usuário ou senha inválidos");});card.addView(login,margin(-1,dp(52),14,0));
    Button reg=button("Criar conta");reg.setOnClickListener(v->{String u=user.getText().toString().trim(),p=pass.getText().toString();if(u.length()<3||p.length()<4){toast("Use usuário e senha válidos");return;}prefs.edit().putString("u",u).putString("p",p).apply();showHome(u);});card.addView(reg,margin(-1,dp(52),8,0));
  }

  TextView heading(String title,String subtitle){
    TextView h=tv(title,24,WHITE);h.setTypeface(null,1);body.addView(h,margin(-1,dp(38),2,0));
    TextView s=tv(subtitle,14,MUTED);body.addView(s,margin(-1,dp(32),2,8));return h;
  }
  void showHome(String u){
    base();
    LinearLayout top=new LinearLayout(this);top.setGravity(Gravity.CENTER_VERTICAL);TextView brand=tv("⚡ BRcode",25,GREEN);brand.setTypeface(null,1);top.addView(brand,lp(0,dp(54)));((LinearLayout.LayoutParams)brand.getLayoutParams()).weight=1;
    Button profile=button("👤");profile.setOnClickListener(v->showProfile(u));top.addView(profile,lp(dp(52),dp(48)));root.addView(top);
    TextView welcome=tv("Olá, "+u+"! 👋",27,WHITE);welcome.setTypeface(null,1);root.addView(welcome,margin(-1,dp(42),8,0));
    TextView hint=tv("Seu espaço para conversar, jogar e criar comunidades.",14,MUTED);root.addView(hint,margin(-1,dp(34),0,10));
    body=new LinearLayout(this);body.setOrientation(LinearLayout.VERTICAL);root.addView(body,lp(-1,0));((LinearLayout.LayoutParams)body.getLayoutParams()).weight=1;
    feature("💬","Chat","Converse por texto",v->showChat(u));
    feature("🎙️","Voz","Entre em uma sala de voz",v->showVoice(u));
    feature("👥","Comunidades","Servidores e canais",v->showCommunities(u));
    feature("🤖","Bots","Automação e comandos",v->showBots(u));
    feature("👫","Amigos","Mensagens privadas",v->showFriends(u));
    feature("🛡️","Moderação","Permissões e segurança",v->showModeration(u));
    nav(u,"home");
  }
  void feature(String icon,String title,String desc,View.OnClickListener click){
    LinearLayout row=new LinearLayout(this);row.setGravity(Gravity.CENTER_VERTICAL);row.setPadding(dp(12),dp(4),dp(8),dp(4));row.setBackground(bg(PANEL,BORDER,14));
    TextView i=tv(icon,25,WHITE);row.addView(i,lp(dp(50),dp(58)));
    LinearLayout texts=new LinearLayout(this);texts.setOrientation(LinearLayout.VERTICAL);TextView t=tv(title,17,WHITE);t.setTypeface(null,1);texts.addView(t);texts.addView(tv(desc,12,MUTED));row.addView(texts,lp(0,dp(58)));((LinearLayout.LayoutParams)texts.getLayoutParams()).weight=1;
    TextView arrow=tv("›",28,GREEN);row.addView(arrow,lp(dp(28),dp(58)));row.setOnClickListener(click);body.addView(row,margin(-1,dp(66),5,0));
  }

  void nav(String u,String selected){
    LinearLayout n=new LinearLayout(this);n.setGravity(Gravity.CENTER);n.setPadding(0,dp(5),0,0);String[] labels={"⌂\nInício","💬\nChat","👥\nComunidades","👤\nPerfil"};
    for(String s:labels){Button b=button(s);b.setTextSize(11);b.setGravity(Gravity.CENTER);b.setBackgroundColor(Color.TRANSPARENT);String key=s.startsWith("⌂")?"home":s.startsWith("💬")?"chat":s.startsWith("👥")?"communities":"profile";if(key.equals(selected))b.setTextColor(GREEN);else b.setTextColor(MUTED);b.setOnClickListener(v->{if(key.equals("home"))showHome(u);else if(key.equals("chat"))showChat(u);else if(key.equals("communities"))showCommunities(u);else showProfile(u);});n.addView(b,lp(0,dp(58)));((LinearLayout.LayoutParams)b.getLayoutParams()).weight=1;}
    root.addView(n,lp(-1,dp(63)));
  }
  void pageTop(String title,String back){
    LinearLayout top=new LinearLayout(this);top.setGravity(Gravity.CENTER_VERTICAL);Button b=button("‹");b.setTextSize(28);b.setOnClickListener(v->showHome(prefs.getString("u","Caua_")));top.addView(b,lp(dp(50),dp(50)));TextView t=tv(title,22,WHITE);t.setTypeface(null,1);top.addView(t,lp(0,dp(50)));((LinearLayout.LayoutParams)t.getLayoutParams()).weight=1;root.addView(top);
  }
  void simplePage(String title,String subtitle,String[] items){
    base();pageTop(title,"‹");body=new LinearLayout(this);body.setOrientation(LinearLayout.VERTICAL);root.addView(body,lp(-1,0));((LinearLayout.LayoutParams)body.getLayoutParams()).weight=1;heading(title,subtitle);for(String x:items){Button b=button(x);b.setOnClickListener(v->toast(x+" selecionado"));body.addView(b,margin(-1,dp(56),6,0));}nav(prefs.getString("u","Caua_"),title.equals("Chat")?"chat":title.equals("Comunidades")?"communities":"home");
  }
  void showChat(String u){
    base();pageTop("Chat","‹");body=new LinearLayout(this);body.setOrientation(LinearLayout.VERTICAL);root.addView(body,lp(-1,0));((LinearLayout.LayoutParams)body.getLayoutParams()).weight=1;
    heading("# geral","Canal principal • online");
    ScrollView scroll=new ScrollView(this);LinearLayout messages=new LinearLayout(this);messages.setOrientation(LinearLayout.VERTICAL);messages.setPadding(0,dp(8),0,dp(8));scroll.addView(messages);body.addView(scroll,lp(-1,0));((LinearLayout.LayoutParams)scroll.getLayoutParams()).weight=1;
    addMessage(messages,"BRcode","Bem-vindo ao canal geral! 👋",true);addMessage(messages,"Caua_","Agora consigo conversar pelo celular.",false);
    LinearLayout input=new LinearLayout(this);input.setGravity(Gravity.CENTER_VERTICAL);EditText edit=new EditText(this);edit.setHint("Digite uma mensagem...");edit.setSingleLine(true);edit.setTextColor(WHITE);edit.setHintTextColor(MUTED);edit.setBackground(bg(PANEL,BORDER,14));input.addView(edit,lp(0,dp(52)));((LinearLayout.LayoutParams)edit.getLayoutParams()).weight=1;Button send=button("➤");send.setBackground(bg(GREEN,0,12));send.setTextColor(Color.BLACK);send.setOnClickListener(v->{String m=edit.getText().toString().trim();if(!m.isEmpty()){addMessage(messages,u,m,false);edit.setText("");scroll.post(()->scroll.fullScroll(View.FOCUS_DOWN));}});input.addView(send,margin(dp(52),dp(52),0,0));root.addView(input);nav(u,"chat");
  }
  void addMessage(LinearLayout box,String who,String msg,boolean bot){LinearLayout card=new LinearLayout(this);card.setOrientation(LinearLayout.VERTICAL);card.setPadding(dp(12),dp(8),dp(12),dp(8));TextView a=tv(who,14,bot?GREEN:WHITE);a.setTypeface(null,1);card.addView(a);card.addView(tv(msg,14,MUTED));box.addView(card,margin(-1,-2,2,2));}
  void showVoice(String u){simplePage("Voz","Salas de voz disponíveis",new String[]{"🎙️  Sala Geral • 3 online","🎮  Gaming • 5 online","🎵  Música • 2 online"});}
  void showCommunities(String u){simplePage("Comunidades","Entre em uma comunidade ou crie a sua",new String[]{"🏠  BRcode Brasil","🎮  Gaming Brasil","➕  Criar comunidade"});}
  void showBots(String u){simplePage("Bots","Bots disponíveis no BRcode",new String[]{"🤖  BRcode Bot • online","🎵  Music Bot • online","➕  Adicionar bot"});}
  void showFriends(String u){simplePage("Amigos","Seus amigos e mensagens privadas",new String[]{"🟢  Nenhum amigo online","➕  Adicionar amigo"});}
  void showModeration(String u){simplePage("Moderação","Ferramentas do proprietário",new String[]{"🛡️  Permissões","🔨  Banimentos","🔇  Silenciados","📋  Registro de ações"});}
  void showProfile(String u){base();pageTop("Perfil","‹");body=new LinearLayout(this);body.setOrientation(LinearLayout.VERTICAL);root.addView(body,lp(-1,0));((LinearLayout.LayoutParams)body.getLayoutParams()).weight=1;heading(u,"Proprietário • conta local");Button account=button("👑  Proprietário do BRcode");account.setTextColor(GREEN);body.addView(account,margin(-1,dp(60),4,0));Button logout=button("Sair da conta");logout.setOnClickListener(v->showAuth());body.addView(logout,margin(-1,dp(56),12,0));nav(u,"profile");}
  void toast(String s){Toast.makeText(this,s,Toast.LENGTH_SHORT).show();}
}
