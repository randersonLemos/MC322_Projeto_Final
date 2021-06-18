package mc322.lab07.control.controle;

public interface IControle extends 
  IRConstrutorControle 
, IRCircuitoControle
, IRPilotoControle
, IRPainelControle 
{
	public void comecarJogo();
}