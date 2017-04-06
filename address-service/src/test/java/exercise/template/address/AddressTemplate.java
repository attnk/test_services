package exercise.template.address;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import exercise.model.Address;

public class AddressTemplate implements TemplateLoader {
	
	@Override
	public void load() {
		Fixture.of(Address.class).addTemplate("default", new Rule(){{
			add("street", "");
			add("district", "");
			add("city", "");
			add("state", "");
		}});
		
		Fixture.of(Address.class).addTemplate("paulista", new Rule(){{
			add("street", "Av. Paulista");
			add("district", "Paraíso");
			add("city", "São Paulo");
			add("state", "SP");
		}});
	}

}
