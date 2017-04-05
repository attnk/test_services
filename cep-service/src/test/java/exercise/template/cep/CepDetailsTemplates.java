package exercise.template.cep;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import exercise.entity.CepDetails;

public class CepDetailsTemplates implements TemplateLoader{

	@Override
	public void load() {
		Fixture.of(CepDetails.class).addTemplate("default", new Rule(){{
			add("id", 0L);
			add("street", "");
			add("district", "");
			add("city", "");
			add("state", "");
			add("cep", 12345678);
		}});
		
		Fixture.of(CepDetails.class).addTemplate("paulista", new Rule(){{
			add("id", 1L);
			add("street", "Av. Paulista");
			add("district", "Paraíso");
			add("city", "São Paulo");
			add("state", "SP");
			add("cep", "01311000");
		}});
		
		Fixture.of(CepDetails.class).addTemplate("candido-de-abreu", new Rule(){{
			add("id", 2L);
			add("street", "Av. Cândido de Abreu");
			add("district", "Centro Cívico");
			add("city", "Curitiba");
			add("state", "PR");
			add("cep", "80530908");
		}});
		
		Fixture.of(CepDetails.class).addTemplate("estadio-maracana", new Rule(){{
			add("id", 3L);
			add("street", "Av. Pres. Castelo Branco");
			add("district", "Maracanã");
			add("city", "Rio de Janeiro");
			add("state", "RJ");
			add("cep", "20271130");
		}});
	}

}
