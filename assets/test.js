window.onload= function(){
    var personaldata = window.PersonalData.getPersonalData(); /*window.PersonalData <--> java对象:WebviewTest的实例*/
    if(personaldata)
    {
        var Pd= document.getElementById("Personaldata");
	pnode = document.createElement("p");
        tnode = document.createTextNode("ID:" + personaldata.getID());
        pnode.appendChild(tnode);
        Pd.appendChild(pnode);
	pnode = document.createElement("p");
        tnode = document.createTextNode("Name:" + personaldata.getName());
        pnode.appendChild(tnode);
        Pd.appendChild(pnode);
	pnode = document.createElement("p");
        tnode = document.createTextNode("Age:" + personaldata.getAge());
        pnode.appendChild(tnode);
        Pd.appendChild(pnode);
	pnode = document.createElement("p");
        tnode = document.createTextNode("Blog:" + personaldata.getBlog());
        pnode.appendChild(tnode);
        Pd.appendChild(pnode);
    }    
}