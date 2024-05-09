import { Component } from '@angular/core';
import { ProductService } from '../product.service';
import { ActivatedRoute, Router } from '@angular/router';
import { OnInit } from '@angular/core';
import { Product } from '../product.model';
import { HeaderService } from '../../template/header/header.service';

@Component({
  selector: 'app-product-delete',
  templateUrl: './product-delete.component.html',
  styleUrl: './product-delete.component.css'
})
export class ProductDeleteComponent implements OnInit{

  product: Product;

  constructor(private productService: ProductService, private router: Router, private route: ActivatedRoute, private headerService: HeaderService){
    headerService.headerData = {
      title: "Excluir Produto",
      icon: "delete",
      routeUrl: "/products/delete"
    }
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id !== null) {
      this.productService.readById(id).subscribe(product => {
        this.product = product;
      });
    }
  }

  deleteProduct(): void{
    const id = this.route.snapshot.paramMap.get('id');
    if(id!==null){
      this.productService.delete(id).subscribe(()=>{
        this.productService.showMessage("Produto Deletado com sucesso!")
        this.router.navigate(['/products'])
      })
    }
  }

  cancel(): void {
    this.router.navigate(['/products'])
  }



}
