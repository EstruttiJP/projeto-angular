import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { ProductService } from '../product.service';
import { Product } from '../product.model';
import { HeaderService } from '../../template/header/header.service';

@Component({
  selector: 'app-product-read-client',
  templateUrl: './product-read-client.component.html',
  styleUrl: './product-read-client.component.css'
})
export class ProductReadClientComponent implements OnInit {

  products: Product[] = [];
  productName = ""

  constructor(private productService: ProductService, private headerService: HeaderService) {
    headerService.headerData = {
      title: "Produtos",
      icon: "assignment",
      routeUrl: "/products"
    }
  }

  ngOnInit(): void {
    this.readProducts();
  }


  pageSize = 8;
  pageIndex = 0;
  totalElements = 15;
  direction = 'asc';

  hidePageSize = false;
  showPageSizeOptions = false;
  disabled = false;

  pageEvent: PageEvent;

  handlePageEvent(e: PageEvent) {
    this.pageEvent = e;
    this.totalElements = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    this.readProducts();
  }

  readProducts() {
    this.productService.read(this.pageIndex, this.pageSize, this.direction).subscribe(response => {
      this.products = response._embedded?.productVOList || [];
      this.totalElements = response.page.totalElements;
    })
  }

  findProductsByName() {
    this.productService.readByName(this.productName, 0, this.pageSize, this.direction).subscribe(response => {
      this.products = response._embedded?.productVOList || [];
      this.totalElements = response.page.totalElements;
    })
  }

}
