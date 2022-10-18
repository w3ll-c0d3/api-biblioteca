package br.com.residencia.biblioteca.service;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.residencia.biblioteca.dto.AlunoDTO;
import br.com.residencia.biblioteca.entity.Aluno;
import br.com.residencia.biblioteca.repository.AlunoRepository;

@Service
public class AlunoService {
	@Autowired
	AlunoRepository alunoRepository; 
	
	@Autowired
	private ModelMapper modelMapper;

//	Get
	public List<Aluno> getAllAlunos() {
		return alunoRepository.findAll();	
	}
	
	public List<AlunoDTO> getAllAlunosDTO() {
		List<Aluno> aluno = getAllAlunos();
		List<AlunoDTO> alunoDTO = new ArrayList<AlunoDTO>();
		aluno.forEach(aln -> {
			alunoDTO.add(converteEntitytoDTO(aln));
		});
		return alunoDTO;
	}
	
	public Aluno getAlunoById(Integer id) {
		return alunoRepository.findById(id).orElse(null);
	}
	
	public AlunoDTO getAlunoDtoById(Integer id) {
		Aluno aluno = alunoRepository.findById(id).orElse(null);
		if(aluno != null) {
			return converteEntitytoDTO(aluno);
		}
		return null;
	}

//	Save
	public Aluno saveAluno(Aluno aluno) {
		return alunoRepository.save(aluno);
	}
	
	public AlunoDTO saveAlunoDTO(AlunoDTO alunoDTO) {
		Aluno aluno = toEntity(alunoDTO);
		Aluno novoAluno = alunoRepository.save(aluno);
		
		AlunoDTO alunoAtualizadoDTO = converteEntitytoDTO(novoAluno);
		return alunoAtualizadoDTO;
	}

//	Update
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
	
	public AlunoDTO updateAlunoDTO(AlunoDTO alunoDTO, Integer id) {
		Aluno alunoExistenteNoBanco = getAlunoById(id);
		AlunoDTO alunoAtualizadoDTO = new AlunoDTO();
		if(alunoExistenteNoBanco != null) {
			alunoExistenteNoBanco = toEntity(alunoDTO);
			Aluno alunoAtualizado = alunoRepository.save(alunoExistenteNoBanco);
			alunoAtualizadoDTO = converteEntitytoDTO(alunoAtualizado);
		}
		return alunoAtualizadoDTO;
	}

//	Delete
	public Aluno deleteAluno(Integer id) {
		alunoRepository.deleteById(id);
		return getAlunoById(id);
	}
	
	public AlunoDTO deleteAlunoDTO(Integer id) {
		alunoRepository.deleteById(id);
		return getAlunoDtoById(id);
	}
		
//	public Boolean deleteAlunoBool(Integer id) {
//		alunoRepository.deleteById(id);
//		if(getAlunoById(id) != null) {
//			return false;
//		} else {
//			return true;
//		}
//	}
	
//	Convert
	private AlunoDTO converteEntitytoDTO(Aluno aluno) {
		AlunoDTO alunoDTO = new AlunoDTO();
		alunoDTO = (modelMapper.map(aluno, AlunoDTO.class));
		return alunoDTO;	
	}
	
	private Aluno toEntity(AlunoDTO alunoDTO) {
		Aluno aluno= new Aluno();
		aluno.setNome(alunoDTO.getNome());
		aluno.setBairro(alunoDTO.getBairro());
		aluno.setCidade(alunoDTO.getCidade());
		aluno.setComplemento(alunoDTO.getComplemento());
		aluno.setCpf(alunoDTO.getCpf());
		aluno.setDataNascimento(alunoDTO.getDataNascimento());
		aluno.setLogradouro(alunoDTO.getLogradouro());
		aluno.setNumeroLogradouro(alunoDTO.getNumeroLogradouro());
		
		return aluno;	
	}
}
