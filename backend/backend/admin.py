from django.contrib import admin
from django.contrib.auth.admin import UserAdmin

from . import models


@admin.register(models.User)
class UserAdmin(UserAdmin):
    actions_on_bottom = True
    actions_on_top = False
    # fields = [
    #     'username', 'password', 'last_name', 'first_name', 'second_name', 'email',
    #     'is_superuser', 'is_staff', 'is_active',
    # ]
    list_display = [
        'username', 'last_name', 'first_name', 'second_name',
        'is_superuser', 'is_staff', 'is_active'
    ]
    list_display_links = [
        'username', 'last_name', 'first_name', 'second_name'
    ]
    search_fields = [
        'username', 'last_name', 'first_name', 'second_name'
    ]

    def __init__(self, *argv, **kwargs):
        self.fieldsets[1][1]['fields'] = (
            'last_name', 'first_name', 'second_name', 'email'
        )
        super().__init__(*argv, **kwargs)


@admin.register(models.Course)
class CourseAdmin(admin.ModelAdmin):
    actions_on_bottom = True
    actions_on_top = False
    list_display = ['name']
    search_fields = ['name']


@admin.register(models.CourseTopic)
class CourseTopicAdmin(admin.ModelAdmin):
    actions_on_bottom = True
    actions_on_top = False
    list_display = ['course', 'code', 'name']
    list_filter = ['course']


@admin.register(models.Tag)
class TagTopicAdmin(admin.ModelAdmin):
    actions_on_bottom = True
    actions_on_top = False
    list_display = ['name']


admin.site.site_header = 'Администрирование'
admin.site.site_title = 'Администрирование'
admin.site.index_title = 'Таблицы'
