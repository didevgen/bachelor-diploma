import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { Observable } from 'rxjs';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UnsubscribableComponent } from '../../../theme/unsubscribable.component';
import { AuthHttp } from '../../../services/http/auth.http';
import { CategoryClient } from '../categories.client';
import { ToasterService } from 'angular2-toaster';
import { Category, DetailCategory } from '../../../models/category/category.models';

@Component({
  selector: 'category-form',
  templateUrl: './category-form.html',
  styleUrls: ['./category-form.scss']
})
export class CategoryFormComponent extends UnsubscribableComponent implements OnInit {

  public categoryForm: FormGroup;

  public uuid: string;

  constructor(private fb: FormBuilder,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private toasterService: ToasterService,
              private categoryClient: CategoryClient,
              private _sanitizer: DomSanitizer,
              private authHttp: AuthHttp) {
    super();
  }

  public ngOnInit(): void {
    this.uuid = this.activatedRoute.snapshot.params['uuid'];
    this.initForm();
    if (this.uuid) {
      this.categoryClient.getCategory(this.uuid).subscribe((result: DetailCategory) => {
        this.updateForm(result);
      });
    }
  }

  public addChild(value: any = null): void {
    (<FormArray>this.categoryForm.controls['children']).push(this.fb.group({child: value || ''}));
  }

  public autocompleListFormatter = (data: any): SafeHtml => {
    return this._sanitizer.bypassSecurityTrustHtml(`<span>${data.name}</span>`);
  }

  public autocompleteValueFormatter(data: any): string {
    return data.name;
  }

  public removeChildCategory(index: number): void {
    (<FormArray>this.categoryForm.controls['children']).removeAt(index);
  }

  public createCategory(): void {
    const raw: any = this.categoryForm.getRawValue();
    const result: any = {};
    result.name = raw.name;
    result.parent = raw.parent ? raw.parent.uuid : null;
    result.children = [];
    if (raw.children) {
      result.children = raw.children.map(item => item.child.uuid);
    }
    if (!this.uuid) {
      this.categoryClient.createCategory(result).subscribe((data: Category) => {
        this.toasterService.pop('success', 'Successfully saved category', 'Success');
        this.router.navigate(['/pages/categories', data.uuid]);
      }, error => {
        this.toasterService.pop('error', 'Error', 'Failed to save category');
      });
    } else {
      this.categoryClient.updateCategory(this.uuid, result).subscribe((data: Category) => {
        this.toasterService.pop('success', 'Successfully updated category', 'Success');
        this.router.navigate(['/pages/categories', data.uuid]);
      }, error => {
        this.toasterService.pop('error', 'Error', 'Failed to update category');
      });
    }
  }

  public observableSource = (keyword: any): Observable<any[]> => {
    const url: string = `/api/v1/categories/find/name?&name=${keyword}&limit=10`;
    if (keyword) {
      return this.authHttp.get(url)
        .map(res => {
          return res.data;
        });
    } else {
      return Observable.of([]);
    }
  }

  private updateForm(obj: DetailCategory): void {
    this.categoryForm.setValue({
      name: obj.name || '',
      parent: obj.parent,
      children: []
    });
    obj.children.forEach(child => {
      this.addChild(child);
    });
  }

  private initForm(): void {
    this.categoryForm = this.fb.group({
      name: ['', Validators.required],
      parent: [''],
      children: this.fb.array([])
    });
  }
}
