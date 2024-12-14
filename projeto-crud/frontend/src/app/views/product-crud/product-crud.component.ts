import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HeaderService } from '../../components/template/header/header.service';

@Component({
  selector: 'app-product-crud',
  templateUrl: './product-crud.component.html',
  styleUrl: './product-crud.component.css'
})
export class ProductCrudComponent implements OnInit{
  isAuth = false;
  constructor(private router: Router, private headerService: HeaderService){
    headerService.headerData = {
      title: "Cadastro de Produtos",
      icon: "storefront",
      routeUrl: "/products"
    }
  }
  navigateToProductCreate(): void{
    this.router.navigate(["/products-admin/create"]);
  }
  ngOnInit(): void {
    this.isAuth = this.headerService.isAuthenticated()
  }
}
