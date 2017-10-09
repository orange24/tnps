
//Hide / Display item
function doHideShow(id){
	alert(id);
    var div  = document.getElementById(id);
    var elem = div.style;
    if(div){
        if(elem.display == 'none'){
            elem.display = 'block';
        }else{
            elem.display = 'none';
        }
    }
}

//Diplay item
function doShow(id){
    var div  = document.getElementById(id);
    var elem = div.style;
    if(div){
        elem.display = 'block';
    }
}
    
//Clear Calendar Input    
function clearCalendar(id){
    var inp = document.getElementById(id);
    if(inp){                    
        inp.value = "";                
    }
}

//Change Parent Frame
function changeParentFrame(pid, nid){
    var parentFrame = parent.document.getElementById(pid);
    if(parentFrame){
        parentFrame.src = nid;
    }
}

//Change of select options
function onSelectChange(pid, nid){
    var selVal = document.getElementById(nid);
    if(selVal){
        changeParentFrame(pid, selVal.value);
    }    
}


//Add upload row
var uploadCounter = 0;
var inpCounter    = 0;



//Pop up window
function popUp(filename){
    window.open(filename, "", "status = 1, height= 500, width= 450, resizable ");
}

function popUp(filename, height, width){
    window.open(filename, "", "status = 1, height= "+height+", width= "+width+", resizable, scrollbars");
}


//change the frame src
function changeParentFrame(frameid, file){                
    parent.document.getElementById(frameid).src= file;
}


//check input
function chkInp(inp, inpName, output){
    var inpID = document.getElementById(inp);
    if(inpID){
        if(inpID.value == ''){
            alert('Please enter value for '+inpName + '.');
        }else{
            doShow(output);
        }
    }
}


//Changing the screen to specify path.
	function goTo(path){
				window.location= path;
			}
			
//Open PopUp
function showPopup(varPath,w,h){
	var answer = window.showModalDialog(varPath,'mypopup',"dialogWidth:"+w+"px; dialogHeight:"+h+"px; center:yes");
}


