import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
import { UnsubscribableComponent } from '../../../theme/unsubscribable.component';
import { HolderClient } from '../holder.client';

@Component({
  selector: 'holder-form',
  styleUrls: ['./holder-form.scss'],
  templateUrl: './holder-form.component.html'
})
export class HolderFormComponent extends UnsubscribableComponent implements OnInit {

  public form: FormGroup;

  constructor(private fb: FormBuilder,
              private holderClient: HolderClient) {
    super();
  }

  public ngOnInit(): void {
    this.initForm();
  }

  public addCard(value: any = null): void {
    (<FormArray>this.form.controls['cards']).push(this.fb.group({card: value || ''}));
  }

  public addCategory(value: any = null): void {
    (<FormArray>this.form.controls['categories']).push(this.fb.group({category: value || ''}));
  }

  public removeCategory(index: number): void {
    (<FormArray>this.form.controls['categories']).removeAt(index);
  }

  public removeCard(index: number): void {
    (<FormArray>this.form.controls['cards']).removeAt(index);
  }

  private initForm(): void {
    this.form = this.fb.group({
      fullName: ['', Validators.required],
      cards: this.fb.array([]),
      categories: this.fb.array([])
    });
  }

}
