import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ItemInfo} from "../shared/models/itemInfo";
import {ItemHttpService} from "../shared/http/item-http.service";

@Component({
  selector: 'app-item-registration',
  templateUrl: './item-registration.component.html',
  styleUrls: ['./item-registration.component.scss']
})
export class ItemRegistrationComponent implements OnInit {
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private itemHttpService: ItemHttpService) {
  }

  ngOnInit() {
    this.form = this.createForm();
  }

  createForm(): FormGroup {
    return this.fb.group({
      itemName: ['', [Validators.required, Validators.maxLength(50)]],
      itemComment:['', [Validators.maxLength(150)]],
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

    this.itemHttpService.createItem(itemInfo).subscribe()
  }
}
