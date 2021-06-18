package mc322.lab07.model.circuito;

import mc322.lab07.model.elemento.Elemento;

public class Circuito implements ICircuito{
	private Elemento matriz[][] = null;
	private int maxLin = -1;
	private int maxCol = -1;
	
	public void setMatriz(int maxLin, int maxCol, Elemento matriz[][])
	{
		this.maxLin = maxLin;
		this.maxCol = maxCol;
		this.matriz = matriz;		
	}
	
	
	public int getMaxLin()
	{
		return maxLin;
	}
	
	
	public int getMaxCol()
	{
		return maxCol;
	}
	
	
	public Elemento getElemento(int lin, int col) throws TrataExcecaoAcessoMatrizCircuito
	{
		if(matriz != null)
		{
			if(lin >= 0 & lin < maxLin & col >= 0 & col < maxCol)
			{
				return matriz[lin][col];
			}
			else
			{
				throw new TrataExcecaoAcessoMatrizCircuito("Indice de linha ou coluna fora da matriz do circuito!");
			}
		}
		return null;
	}
	
	
	public void setElemento(Elemento elemento) throws TrataExcecaoAcessoMatrizCircuito
	{
		int lin = elemento.getLin();
		int col = elemento.getCol();
		if(lin >= 0 & lin < maxLin & col >= 0 & col < maxCol)
		{
			matriz[lin][col] = elemento;
		}
		else
		{
			throw new TrataExcecaoAcessoMatrizCircuito("Indice de linha ou coluna fora da matriz do circuito!");
		}
	}
}
