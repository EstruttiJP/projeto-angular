import { Component } from '@angular/core';
import { ProductService } from '../product.service';
import { ActivatedRoute, Router } from '@angular/router';
import { OnInit } from '@angular/core';
import { Product } from '../product.model';
import { HeaderService } from '../../template/header/header.service';


@Component({
  selector: 'app-product-update',
  templateUrl: './product-update.component.html',
  styleUrl: './product-update.component.css'
})

export class ProductUpdateComponent implements OnInit {

  product: Product;

  constructor(private productService: ProductService, private router: Router, private route: ActivatedRoute, private headerService: HeaderService){
    headerService.headerData = {
      title: "Alteração de Produto",
      icon: "storefront",
      routeUrl: "/products/update"
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
  updateProduct(): void {
    this.productService.update(this.product).subscribe(() => {
      this.productService.showMessage('alteração realizada com sucesso!');
      this.router.navigate(['/products'])
    });
  }
  cancel(): void {
    this.router.navigate(['/products'])
  }
}
