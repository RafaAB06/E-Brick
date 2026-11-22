package br.edu.unoesc.ebrick.validator;

public final class Validator {
	private Validator() {};
	
	public static boolean validarCPF(String cpf) {
		String regex = "^[0-9]{11}$";
		return cpf != null && cpf.matches(regex);
	}
	
	public static boolean validarCNPJ(String cnpj) {
		String regex = "^[0-9]{18}$";
		return cnpj != null && cnpj.matches(regex);
	}
	
	public static boolean validarTelefone(String telefone) {
		String regex = "^[0-9]{10,11}$";
		return telefone != null && telefone.matches(regex);
	}
}
