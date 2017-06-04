import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { Observable } from 'rxjs';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UnsubscribableComponent } from '../../../theme/unsubscribable.component';
import { AuthHttp } from '../../../services/http/auth.http';
import { CategoryClient } from '../categories.client';
import { ToasterService } from 'angular2-toaster';
import { Category } from '../../../models/category/category.models';

@Component({
  selector: 'category-form',
  templateUrl: './category-form.html',
  styleUrls: ['./category-form.scss']
})
export class CategoryFormComponent extends UnsubscribableComponent implements OnInit {

  public categoryForm: FormGroup;

  constructor(private fb: FormBuilder,
              private router: Router,
              private toasterService: ToasterService,
              private categoryClient: CategoryClient,
              private _sanitizer: DomSanitizer,
              private authHttp: AuthHttp) {
    super();
  }

  public ngOnInit(): void {
    this.initForm();
  }

  public addChild(): void {
    (<FormArray>this.categoryForm.controls['children']).push(this.fb.group({child: ''}));
  }

  public autocompleListFormatter = (data: any): SafeHtml => {
    let html = `<span>${data.name}</span>`;
    return this._sanitizer.bypassSecurityTrustHtml(html);
  }

  public removeChildCategory(index: number): void {
    (<FormArray>this.categoryForm.controls['children']).removeAt(index);
  }

  public createCategory(): void {
    const raw: any = this.categoryForm.getRawValue();
    const result: any = {};
    result.name = raw.name;
    result.parent = raw.parent.uuid;
    if (!result.parent) {
      result.parent = null;
    }
    result.children = [];
    if (raw.children) {
      result.children = raw.children.map(item => item.child.uuid);
    }
    this.categoryClient.createCategory(result).subscribe((data: Category) => {
      this.toasterService.pop('success', 'Successfully saved category', 'Success');
      this.router.navigate(['/pages/categories', data.uuid]);
    }, error => {
      this.toasterService.pop('error', 'Error', 'Failed to save category');
    });
  }

  public observableSource = (keyword: any): Observable<any[]> => {
    const url: string =
      `/api/v1/categories/find/name?&name=${keyword}&limit=10`;
    if (keyword) {
      return this.authHttp.get(url)
        .map(res => {
          return res.data;
        });
    } else {
      return Observable.of([]);
    }
  }

  private initForm(): void {
    this.categoryForm = this.fb.group({
      name: ['', Validators.required],
      parent: [''],
      children: this.fb.array([])
    });
  }
}
