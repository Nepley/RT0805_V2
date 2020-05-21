//Gestion d'AJAX
var requeteHTTP = new XMLHttpRequest(); // Création de l'objet
requeteHTTP.onloadend = handler;        // Spécification de l'handler à exécuter
var id_choisi = 0;                      //Usage de l'ajax
var ligne_choisi = null;

function handler()
{
    if((requeteHTTP.readyState == 4) && (requeteHTTP.status == 200))
    {
        ligne_choisi.parentNode.removeChild(ligne_choisi);
        id_choisi = 0;
        ligne_choisi =
        document.getElementById("confirmation").className = "alert alert-success";
        document.getElementById("confirmation").innerHTML = "L'utilisateur a bien été supprimé"
    }
    else
    {
        document.getElementById("confirmation").className = "alert alert-danger";
        document.getElementById("confirmation").innerHTML = "Une erreur s'est produite."
    }
}

/*
*Fonction permettant à l'utilisateur de modifier les infos d'un Port en libérant les inputs
*/
function modifierInfos(ligne)
{
    id_choisi = ligne.querySelector("#identifiant").innerHTML;
    ligne_choisi = ligne;
    console.log(id_choisi);

    var URL = "/visu/admin/users";
    var params = 'id='+ id_choisi;
    requeteHTTP.open("POST", URL, true);
    
    //Send the proper header information along with the request
    requeteHTTP.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    requeteHTTP.send(params);
}
