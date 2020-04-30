import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ItemInfo} from "../shared/models/itemInfo";
import {ItemHttpService} from "../shared/http/item-http.service";
import {Item} from "../../shared/Item";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-item-registration',
  templateUrl: './item-registration.component.html',
  styleUrls: ['./item-registration.component.scss']
})
export class ItemRegistrationComponent implements OnInit {
  form: FormGroup;
  private x: string;

  constructor(
    private fb: FormBuilder,
    private itemHttpService: ItemHttpService,
    private router: Router) {
  }

  ngOnInit() {
    this.form = this.createForm();
  }

  createForm(): FormGroup {
    return this.fb.group({
      itemName: ['Item Name', [Validators.required, Validators.maxLength(50)]],
      itemComment:['What is this ?', [Validators.maxLength(150)]],
      itemQuantity:[1, [Validators.required, Validators.min(1)]],
      itemImageUrl:[null, [Validators.maxLength(500)]]
    });
  }

  onSubmit() {
    const itemInfo: ItemInfo = {
      minimalistId: 1,
      itemName: this.form.get('itemName').value,
      itemComment: this.form.get('itemComment').value,
      itemQuantity: this.form.get('itemQuantity').value,
      itemStatus: 'HAVING',
      itemImageUrl: this.form.get('itemImageUrl').value
    }

    this.itemHttpService.createItem(itemInfo).subscribe();
    this.router.navigate(['/items']).then(() => {
      window.location.reload();
    });

  }

  hasImage(item: string | null): string {
    if (item){
      return item
    }else {
      return 'https://lh3.googleusercontent.com/proxy/4gmh4UQiDWuDED2ZVCTaDfIZfqHa62Vi0kr6XMc650bl1gYXZ65WUWKssJt43S5qDtbMeDl1xzGe3oRO10l4XA-bQsYrvLmposq8Hhs';
    }
  }
}
