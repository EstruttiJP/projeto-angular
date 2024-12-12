package br.com.estruttijp.integrationtests.vo.pagedmodels;

import java.util.List;

import br.com.estruttijp.integrationtests.vo.ProductVO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PagedModelProduct {

	@XmlElement(name = "content")
	private List<ProductVO> content;

	public PagedModelProduct() {}

	public List<ProductVO> getContent() {
		return content;
	}

	public void setContent(List<ProductVO> content) {
		this.content = content;
	}
}
