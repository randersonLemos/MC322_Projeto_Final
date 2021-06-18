package mc322.lab07.model.circuito;

public class TrataExcecaoAcessoMatrizCircuito extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
		public TrataExcecaoAcessoMatrizCircuito()
		{
			super();
		}
		
		public TrataExcecaoAcessoMatrizCircuito(String msg)
		{
			super(msg);
		}
}
