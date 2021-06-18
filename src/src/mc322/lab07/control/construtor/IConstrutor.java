package mc322.lab07.control.construtor;

public interface IConstrutor extends 
  IRCircuitoConstrutor
, IRPilotoConstrutor
, IRPainelConstrutor
, IConstrutorControle
{
	public void construir(int maxLin, int maxCol);
}

