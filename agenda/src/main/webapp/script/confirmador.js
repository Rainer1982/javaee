/**
 * Confirmar a exclusao de um contato
 *
 *@author Rainer Barbosa
 * @param idcon
 */

 
 function confirmar(idcon){
	 let resposta = confirm("Tem certeza que quer excluir esse contato?")
	 if(resposta === true){
		 // Redirecionando para o Servlet
		window.location.href = "delete?idcon=" + idcon
	 }
}
	 