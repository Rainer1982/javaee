package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 */
public class DAO {
	
	/**  Módulo de conexão *. */
	// Parâmetros de conexão
	private String driver = "com.mysql.jdbc.Driver";
	
	/** The url. */
	private String url = "jdbc:mysql://localhost:3306/dbagenda?useTimeZone=true&serverTimeZone=UTC";
	
	/** The user. */
	private String user = "root";
	
	/** The password. */
	private String password = "jatoba40";

	/**
	 * Conectar.
	 *
	 * @return the connection
	 */
	// Método de conexão
	private Connection conectar() {

		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	// Teste de conexão

	/**
	 * Teste conexao.
	 */
	public void testeConexao() {
		try {
			Connection con = conectar();
			JOptionPane.showMessageDialog(null, con);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Inserir contato.
	 *
	 * @param contato the contato
	 */
	public void inserirContato(JavaBeans contato) {
		String sql = "INSERT INTO contatos(nome, fone, email) values (?, ?, ?)";
		try {
			// Abrir conexao
			Connection con = conectar();
			// Preparar a query para execucção no banco de dados
			PreparedStatement pst = con.prepareStatement(sql);
			// Substituir os paramêntros (?) pelo conteúdo das variáveis javaBeans
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());

			// Executar a query
			pst.executeUpdate();
			// Encerrar a conexao com banco
			pst.close();
			JOptionPane.showMessageDialog(null, "Contato inserido no banco de dados");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "error --> " + e);
		}
	}

	/**
	 *  CRUD READ *.
	 *
	 * @return the array list
	 */
	public ArrayList<JavaBeans> listarContatos() {
		// Criando um objeto para acessar a classe javaBeans
		ArrayList<JavaBeans> contatos = new ArrayList<>();

		String sql = "SELECT * FROM contatos ORDER BY nome";
		try {
			// Abrir conexao
			Connection con = conectar();
			// Preparar a query para execucção no banco de dados
			PreparedStatement pst = con.prepareStatement(sql);
			// Recebe os valor do banco de dados
			ResultSet rs = pst.executeQuery();
			// o laço abaixo será executado enquanto houver contatos
			while (rs.next()) {
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				// populando o array
				contatos.add(new JavaBeans(idcon, nome, fone, email));
			}
			con.close();
			return contatos;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error -->  " + e);
			return null;
		}
	}

	/**
	 * Selecionar contato.
	 *
	 * @param contato the contato
	 */
	public void selecionarContato(JavaBeans contato) {
		String sql = "SELECT * FROM contatos WHERE idcon = ?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(sql);
			// substituir o interrogação
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				// setar as variaveis javaBeans
				contato.setIdcon(rs.getString(1)); // o getString do ResulSet retorna o valor da coluna. o numero é
													// coluna
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	/**
	 * Alterar contato.
	 *
	 * @param contato the contato
	 */
	public void alterarContato(JavaBeans contato) {

		String sql = "UPDATE contatos SET nome=?, fone=?, email=? WHERE idcon=?";
		try {
			// Abrir conexao
			Connection con = conectar();
			// Preparar a query para execucção no banco de dados
			PreparedStatement pst = con.prepareStatement(sql);
			// Substituir os paramêntros (?) pelo conteúdo das variáveis javaBeans
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());

			// Executar a query
			pst.executeUpdate();
			// Encerrar a conexao com banco
			pst.close();
			JOptionPane.showMessageDialog(null, "Alteção realizada com sucesso");

		} catch (Exception e) {
			System.out.println("Houve um erro " + e);
		}
	}

	/**
	 * Deletar contato.
	 *
	 * @param contato the contato
	 */
	// GRUD DELETE
	public void deletarContato(JavaBeans contato) {

		String sql = "DELETE FROM contatos WHERE idcon=?";
		try {
			// Abrir conexao
			Connection con = conectar();
			// Preparar a query para execucção no banco de dados
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, contato.getIdcon());
			pst.executeUpdate();
			pst.close();
			JOptionPane.showMessageDialog(null, "Contato excluído");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

}
