function getInfo() 
{
    var exdate = new Date();
    exdate.setDate(exdate.getDate()+1);
    var the_cookie_date = exdate.toGMTString();
    if(navigator.javaEnabled() == 1){
        java = 1;
    }else{
        java = 0;    
    }
    var the_cookie = "user_info="+ window.screen.width +"x"+ window.screen.height + "x" + window.screen.colorDepth + "x" + window.screen.availWidth + "x" + window.screen.availHeight + "x" + java;
    var the_cookie = the_cookie + ";expires=" + the_cookie_date;
    document.cookie=the_cookie;
}
function getCookie(c_name)
{
if (document.cookie.length>0)
  {
  c_start=document.cookie.indexOf(c_name + "=");
  if (c_start!=-1)
    { 
    c_start=c_start + c_name.length+1; 
    c_end=document.cookie.indexOf(";",c_start);
    if (c_end==-1) c_end=document.cookie.length;
    return unescape(document.cookie.substring(c_start,c_end));
    } 
  }
return "";
}
getInfo();

