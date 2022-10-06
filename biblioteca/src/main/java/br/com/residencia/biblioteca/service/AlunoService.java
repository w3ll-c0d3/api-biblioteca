package br.com.residencia.biblioteca.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.residencia.biblioteca.entity.Aluno;
import br.com.residencia.biblioteca.repository.AlunoRepository;

@Service
public class AlunoService {
	@Autowired
	AlunoRepository alunoRepository; 

	public List<Aluno> getAllAlunos() {
		return alunoRepository.findAll();	
	}
	
	public Aluno getAlunoById(Integer id) {
		return alunoRepository.findById(id).get();
//		return alunoRepository.findById(id).orElse(null);
	}
	
	public Aluno saveAluno(Aluno aluno) {
		return alunoRepository.save(aluno);
	}
	
	public Aluno updateAluno(Aluno aluno, Integer id) {
		Aluno alunoExistenteNoBanco = getAlunoById(id);
		
		alunoExistenteNoBanco.setBairro(aluno.getBairro());
		alunoExistenteNoBanco.setCidade(aluno.getCidade());
		alunoExistenteNoBanco.setComplemento(aluno.getComplemento());
		alunoExistenteNoBanco.setCpf(aluno.getCpf());
		alunoExistenteNoBanco.setDataNascimento(aluno.getDataNascimento());
		alunoExistenteNoBanco.setLogradouro(aluno.getLogradouro());
		alunoExistenteNoBanco.setNumeroLogradouro(aluno.getNumeroLogradouro());
		alunoExistenteNoBanco.setNome(aluno.getNome());
		
		return alunoRepository.save(alunoExistenteNoBanco);
	}
	
	public Aluno deleteAluno(Integer id) {
		alunoRepository.deleteById(id);
		return getAlunoById(id);
	}
		
//	public Boolean deleteAlunoBool(Integer id) {
//		alunoRepository.deleteById(id);
//		if(getAlunoById(id) != null) {
//			return false;
//		} else {
//			return true;
//		}
//	}
}
