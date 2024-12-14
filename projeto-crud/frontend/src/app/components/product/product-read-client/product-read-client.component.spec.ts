import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductReadClientComponent } from './product-read-client.component';

describe('ProductReadClientComponent', () => {
  let component: ProductReadClientComponent;
  let fixture: ComponentFixture<ProductReadClientComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProductReadClientComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProductReadClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
