package exercise.template.model;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import exercise.model.Address;

public class AddressTemplate implements TemplateLoader {
	
	@Override
	public void load() {
		Fixture.of(Address.class).addTemplate("default", new Rule(){{
			add("id", 0L);
			add("street", "");
			add("district", "");
			add("city", "");
			add("state", "");
			add("number", 0L);
			add("complement", "");
			add("cep", "");
		}});
		
		Fixture.of(Address.class).addTemplate("paulista-com-id", new Rule(){{
			add("id", 1L);
			add("street", "Av. Paulista");
			add("district", "Paraíso");
			add("city", "São Paulo");
			add("state", "SP");
			add("number", 287L);
			add("complement", "cj 16");
			add("cep", "01311000");
		}});
		
		Fixture.of(Address.class).addTemplate("paulista-sem-id", new Rule(){{
			add("id", null);
			add("street", "Av. Paulista");
			add("district", "Paraíso");
			add("city", "São Paulo");
			add("state", "SP");
			add("number", 287L);
			add("complement", "cj 16");
			add("cep", "01311000");
		}});
		
		Fixture.of(Address.class).addTemplate("paulista-sem-cep", new Rule(){{
			add("id", 1L);
			add("street", "Av. Paulista");
			add("district", "Paraíso");
			add("city", "São Paulo");
			add("state", "SP");
			add("number", 287L);
			add("complement", "cj 16");
			add("cep", null);
		}});
		
		Fixture.of(Address.class).addTemplate("paulista-sem-rua", new Rule(){{
			add("id", 1L);
			add("street", null);
			add("district", "Paraíso");
			add("city", "São Paulo");
			add("state", "SP");
			add("number", 287L);
			add("complement", "cj 16");
			add("cep", "01311000");
		}});
		
		Fixture.of(Address.class).addTemplate("paulista-sem-numero", new Rule(){{
			add("id", 1L);
			add("street", "Av. Paulista");
			add("district", "Paraíso");
			add("city", "São Paulo");
			add("state", "SP");
			add("number", null);
			add("complement", "cj 16");
			add("cep", "01311000");
		}});
		
		Fixture.of(Address.class).addTemplate("paulista-sem-cidade", new Rule(){{
			add("id", 1L);
			add("street", "Av. Paulista");
			add("district", "Paraíso");
			add("city", null);
			add("state", "SP");
			add("number", 287L);
			add("complement", "cj 16");
			add("cep", "01311000");
		}});
		
		Fixture.of(Address.class).addTemplate("paulista-sem-estado", new Rule(){{
			add("id", 1L);
			add("street", "Av. Paulista");
			add("district", "Paraíso");
			add("city", "São Paulo");
			add("state", null);
			add("number", 287L);
			add("complement", "cj 16");
			add("cep", "01311000");
		}});
	}

}
