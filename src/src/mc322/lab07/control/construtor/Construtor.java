package mc322.lab07.control.construtor;

import javax.swing.JLabel;

import mc322.lab07.model.circuito.ICircuitoConstrutor;
import mc322.lab07.model.elemento.Elemento;
import mc322.lab07.model.elemento.IPilotoConstrutor;
import mc322.lab07.model.elemento.Livre;
import mc322.lab07.view.painel.IPainelConstrutor;

public class Construtor implements IConstrutor{
	ICircuitoConstrutor icirc = null;
	IPilotoConstrutor ipilo = null;
	IPainelConstrutor ipain = null;


	GeradorDeElementos ge = new GeradorDeElementos();
	

	public void conectar(ICircuitoConstrutor icirc)
	{
		this.icirc = icirc;
	}
	

	public void conectar(IPilotoConstrutor ipilo)
	{
		this.ipilo = ipilo;
	}
	
	
	public void conectar(IPainelConstrutor ipain)
	{
		this.ipain = ipain;
	}

	
	public void construirMatrizDoCircuito(int maxLin, int maxCol)
	{
		Elemento matriz[][] = new Elemento[maxLin][maxCol];
		
		for(int lin=0; lin<maxLin; lin++)
		{
			for(int col=0; col<maxCol; col++)
			{
				if(lin < maxLin - 2)
				{
					Elemento elemento = ge.geradorAleatorioDeElementosSemPiloto(lin, col);
					matriz[lin][col] = elemento;
				}
				else
				{
					matriz[lin][col] = new Livre(lin, col);
				}
			}
		}			
		icirc.setMatriz(maxLin, maxCol, matriz);
	}
	
	
	public void construirMatrizDoPainel(int maxLin, int maxCol)
	{		
		JLabel matriz[][] = new JLabel[maxLin][maxCol];
		
		for(int lin=0; lin<maxLin; lin++)
		{
			for(int col=0; col<maxCol; col++)
			{
				JLabel jlabel = new JLabel();
				matriz[lin][col] = jlabel;
			}
		}		
		ipain.setMatriz(maxLin, maxCol, matriz);		
	}
	
	
	public void construir(int maxLin, int maxCol)
	{	
		this.construirMatrizDoCircuito(maxLin, maxCol);
		this.construirMatrizDoPainel(maxLin, maxCol);
		ipilo.setPosicao(maxLin-1, maxCol/2);
	}
	
	
	public void reiniciarElementosNaMatrizDoCircuito()
	{
		for(int lin=0; lin<icirc.getMaxLin(); lin++)
		{
			for(int col=0; col<icirc.getMaxCol(); col++)
			{
				if(lin < icirc.getMaxLin() - 2)
				{
					Elemento elemento = ge.geradorAleatorioDeElementosSemPiloto(lin, col);
					icirc.setElemento(elemento);					
				}
				else
				{
					icirc.setElemento(new Livre(lin, col));
				}
			}
		}
		ipilo.setPosicao(icirc.getMaxLin()-1, icirc.getMaxCol()/2);		
	}	
	
	
	public void avancarElementosUmaLinhaNoCircuito()
	{
		for(int lin=icirc.getMaxLin() - 1; lin > 0; lin--)
		{
			for(int col=0; col<icirc.getMaxCol(); col++)
			{
				Elemento elem = icirc.getElemento(lin - 1, col);
				elem.setLin(lin);
				elem.setCol(col);
				icirc.setElemento(elem);
			}
		}
		
		for(int col=0; col<icirc.getMaxCol(); col++)
		{
			Elemento elem = ge.geradorAleatorioDeElementosSemPiloto(0, col);
			icirc.setElemento(elem);
		}
	}
}