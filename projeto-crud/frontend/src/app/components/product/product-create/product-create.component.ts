import { ProductService } from '../product.service';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from '../product.model';

@Component({
  selector: 'app-product-create',
  templateUrl: './product-create.component.html',
  styleUrl: './product-create.component.css'
})
export class ProductCreateComponent {

  product: Product = {
    id: "5",
    name: '',
    price: 0
  }

  constructor(private productService: ProductService, private router: Router){}
  createProduct(): void{
    this.productService.create(this.product).subscribe(()=>{
      this.productService.showMessage('operação realizada com sucesso!');
      this.router.navigate(['/products'])
    });
  }
  cancel(): void{
    this.router.navigate(['/products'])
  }
}
