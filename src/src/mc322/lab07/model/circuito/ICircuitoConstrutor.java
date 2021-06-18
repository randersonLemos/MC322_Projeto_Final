package mc322.lab07.model.circuito;

import mc322.lab07.model.elemento.Elemento;

public interface ICircuitoConstrutor 
{
	void setMatriz(int maxLin, int maxCol, Elemento matriz[][]);
	public int getMaxLin();
	public int getMaxCol();
	public void setElemento(Elemento elemento) throws TrataExcecaoAcessoMatrizCircuito;
	public Elemento getElemento(int lin, int col) throws TrataExcecaoAcessoMatrizCircuito;
}
