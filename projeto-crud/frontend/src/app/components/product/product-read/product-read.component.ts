import { PageEvent } from '@angular/material/paginator';
import { Product } from './../product.model';
import { ProductService } from './../product.service';
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { HeaderService } from '../../template/header/header.service';

@Component({
  selector: 'app-product-read',
  templateUrl: './product-read.component.html',
  styleUrl: './product-read.component.css'
})
export class ProductReadComponent implements OnInit{
  
  products: Product[] = [];
  displayedColumns = ['id', 'imageUrl','name', 'price', 'category', 'action']
  
  constructor(private productService: ProductService, private headerService: HeaderService) {
      headerService.headerData = {
        title: "Produtos",
        icon: "assignment",
        routeUrl: "/products"
      }
    }
  
  ngOnInit(): void{
    this.readProducts();
  }


  pageSize = 5;
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

  readProducts(){
    this.productService.read(this.pageIndex, this.pageSize, this.direction).subscribe(response => {
      this.products = response._embedded?.productVOList || [];
      this.totalElements = response.page.totalElements;
    })
  }

}
